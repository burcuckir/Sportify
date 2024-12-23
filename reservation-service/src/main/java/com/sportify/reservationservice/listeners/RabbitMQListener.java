package com.sportify.reservationservice.listeners;

import com.sportify.reservationservice.configuration.RabbitMQConfig;
import com.sportify.reservationservice.queuemessages.OrderCreatedMessage;
import com.sportify.reservationservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.sportify.JsonConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQListener {

    private final OrderService orderService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receiveMessage(String message) {

        OrderCreatedMessage orderCreatedMessage = JsonConverter.convertFromJson(message, OrderCreatedMessage.class);

        if (orderCreatedMessage != null)
            orderService.completeOrder(orderCreatedMessage.getOrderId(), orderCreatedMessage.getUserId(),
                    orderCreatedMessage.getAmount());

    }
}
