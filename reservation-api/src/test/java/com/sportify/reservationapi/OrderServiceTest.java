package com.sportify.reservationapi;

import com.sportify.reservationapi.configuration.RabbitMQConfig;
import com.sportify.reservationapi.entities.*;
import com.sportify.reservationapi.enums.ScheduleStatus;
import com.sportify.reservationapi.exceptions.BasketAmountNotMatchedException;
import com.sportify.reservationapi.exceptions.BasketNotFoundException;
import com.sportify.reservationapi.models.response.OrderListResponse;
import com.sportify.reservationapi.queuemessages.OrderFailedMessage;
import com.sportify.reservationapi.repositories.BasketRepository;
import com.sportify.reservationapi.repositories.OrderRepository;
import com.sportify.reservationapi.repositories.ScheduleRepository;
import com.sportify.reservationapi.services.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sportify.messageservice.RabbitMQMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private RabbitMQMessageService messageService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void completeOrder_shouldSaveOrder_whenBasketMatchesAmount() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Double amount = 100.0;

        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setPrice(100.0);

        List<BasketItem> basketItemList = new ArrayList<>();
        BasketItem basketItem = new BasketItem();
        basketItem.setId(UUID.randomUUID());
        basketItem.setSchedule(schedule);
        basketItemList.add(basketItem);

        Basket basket = new Basket();
        basket.setBasketItems(basketItemList);

        when(basketRepository.findByUserId(userId)).thenReturn(basket);

        orderService.completeOrder(orderId, userId, amount);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(basketRepository, times(1)).save(basket);
        verify(scheduleRepository, atLeastOnce()).save(schedule);
    }

    @Test
    void completeOrder_shouldThrowBasketNotFoundException_whenBasketDoesNotExist() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Double amount = 100.0;

        when(basketRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(BasketNotFoundException.class, () -> orderService.completeOrder(orderId, userId, amount));
        verify(messageService, times(1)).sendMessage(eq(RabbitMQConfig.ORDER_FAILED_QUEUE), any(OrderFailedMessage.class));
    }

    @Test
    void completeOrder_shouldThrowBasketAmountNotMatchedException_whenAmountDoesNotMatch() {
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Double amount = 100.0;

        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setPrice(150.0);

        List<BasketItem> basketItemList = new ArrayList<>();
        BasketItem basketItem = new BasketItem();
        basketItem.setId(UUID.randomUUID());
        basketItem.setSchedule(schedule);
        basketItemList.add(basketItem);

        Basket basket = new Basket();
        basket.setBasketItems(basketItemList);
        when(basketRepository.findByUserId(userId)).thenReturn(basket);

        assertThrows(BasketAmountNotMatchedException.class, () -> orderService.completeOrder(orderId, userId, amount));
    }

    @Test
    void getOrders_shouldReturnOrderListResponse_whenOrdersExist() {
        UUID userId = UUID.randomUUID();

        Branch branch = new Branch();
        branch.setId(UUID.randomUUID());
        branch.setName("Tennis");
        branch.setIsActive(true);

        Facility facility = new Facility();
        facility.setId(UUID.randomUUID());
        facility.setName("Saloon1");
        facility.setBranch(branch);

        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID());
        schedule.setPrice(150.0);
        schedule.setFacility(facility);
        schedule.setIsActive(true);
        schedule.setStatus(ScheduleStatus.AVAILABLE);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID());
        orderItem.setSchedule(schedule);

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(userId);
        order.setOrderItems(List.of(orderItem));

        List<Order> orders = List.of(order);

        when(orderRepository.getOrdersByUserId(userId)).thenReturn(orders);

        OrderListResponse response = orderService.getOrders(userId);

        assertNotNull(response);
        verify(orderRepository, times(1)).getOrdersByUserId(userId);
    }
}
