package com.sportify.reservationapi.listeners;

import com.sportify.reservationapi.configuration.RabbitMQConfig;
import com.sportify.reservationapi.queuemessages.OrderCreatedMessage;
import com.sportify.reservationapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.sportify.jsonconverter.JsonConverter;
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
