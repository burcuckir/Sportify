package com.sportify.reservationservice.services;

import com.sportify.reservationservice.entities.Basket;
import com.sportify.reservationservice.entities.Order;
import com.sportify.reservationservice.entities.OrderItem;
import com.sportify.reservationservice.entities.Schedule;
import com.sportify.reservationservice.enums.PaymentStatus;
import com.sportify.reservationservice.exceptions.BasketNotFoundException;
import com.sportify.reservationservice.mappers.OrderMapper;
import com.sportify.reservationservice.models.response.OrderListResponse;
import com.sportify.reservationservice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    public void completeOrder(UUID orderId, UUID userId, Double amount) {

        Order order = Order.create(orderId, userId, amount, PaymentStatus.SUCCESS);

        List<OrderItem> orderItems = new ArrayList<>();

        Basket basket = basketRepository.findByUserId(userId);
        if (basket == null)
            throw new BasketNotFoundException();

//        if(!basket.getTotalPrice().equals(amount) )
//            throw new RuntimeException("Ödemeyi iptal et.");

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
