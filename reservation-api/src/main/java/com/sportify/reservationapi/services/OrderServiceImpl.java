package com.sportify.reservationapi.services;

import com.sportify.reservationapi.configuration.RabbitMQConfig;
import com.sportify.reservationapi.entities.Basket;
import com.sportify.reservationapi.entities.Order;
import com.sportify.reservationapi.entities.OrderItem;
import com.sportify.reservationapi.entities.Schedule;
import com.sportify.reservationapi.enums.ErrorMessages;
import com.sportify.reservationapi.enums.PaymentStatus;
import com.sportify.reservationapi.exceptions.BasketAmountNotMatchedException;
import com.sportify.reservationapi.exceptions.BasketNotFoundException;
import com.sportify.reservationapi.mappers.OrderMapper;
import com.sportify.reservationapi.models.response.OrderListResponse;
import com.sportify.reservationapi.queuemessages.OrderFailedMessage;
import com.sportify.reservationapi.repositories.BasketRepository;
import com.sportify.reservationapi.repositories.OrderRepository;
import com.sportify.reservationapi.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.sportify.messageservice.RabbitMQMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;

    private final BasketRepository basketRepository;

    private final ScheduleRepository scheduleRepository;

    private final RabbitMQMessageService messageService;

    @Transactional
    public void completeOrder(UUID orderId, UUID userId, Double amount) {
        try {
            Order order = Order.create(orderId, userId, amount, PaymentStatus.SUCCESS);

            List<OrderItem> orderItems = new ArrayList<>();

            Basket basket = basketRepository.findByUserId(userId);
            if (basket == null)
                throw new BasketNotFoundException();

            basket.getBasketItems().forEach(item -> {
                Schedule schedule = item.getSchedule();
                schedule.reserved();

                OrderItem orderItem = OrderItem.create(schedule, order);
                orderItems.add(orderItem);
                scheduleRepository.save(schedule);
            });

            if (!Objects.equals(basket.getTotalAmount(), amount)) {
                fireOrderFailedMessage(orderId, userId, ErrorMessages.BASKET_AMOUNT_NOT_MATCHED.getCODE());
                throw new BasketAmountNotMatchedException();
            }

            order.setOrderItems(orderItems);
            basket.getBasketItems().clear();

            basketRepository.save(basket);
            orderRepository.save(order);
        } catch (Exception ex) {
            fireOrderFailedMessage(orderId, userId, ex.getMessage());
            throw ex;
        }
    }

    public OrderListResponse getOrders(UUID userId) {
        var orders = orderRepository.getOrdersByUserId(userId);
        return OrderMapper.MapToOrderListResponse(orders);
    }

    private void fireOrderFailedMessage(UUID orderId, UUID userId, String errorMessage) {
        var failedMessage = new OrderFailedMessage();
        failedMessage.setOrderId(orderId);
        failedMessage.setUserId(userId);
        failedMessage.setReason(errorMessage);
        messageService.sendMessage(RabbitMQConfig.ORDER_FAILED_QUEUE, failedMessage);
    }
}
