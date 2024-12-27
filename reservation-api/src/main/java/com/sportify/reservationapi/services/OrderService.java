package com.sportify.reservationapi.services;

import com.sportify.reservationapi.entities.Basket;
import com.sportify.reservationapi.entities.Order;
import com.sportify.reservationapi.entities.OrderItem;
import com.sportify.reservationapi.entities.Schedule;
import com.sportify.reservationapi.enums.PaymentStatus;
import com.sportify.reservationapi.exceptions.BasketNotFoundException;
import com.sportify.reservationapi.mappers.OrderMapper;
import com.sportify.reservationapi.models.response.OrderListResponse;
import com.sportify.reservationapi.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final BasketRepository basketRepository;

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void completeOrder(UUID orderId, UUID userId, Double amount) {

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

        order.setOrderItems(orderItems);
        basket.getBasketItems().clear();

        basketRepository.save(basket);
        orderRepository.save(order);
    }

    public OrderListResponse getOrders(UUID userId) {
        var orders = orderRepository.getOrdersByUserId(userId);
        return OrderMapper.MapToOrderListResponse(orders);
    }
}
