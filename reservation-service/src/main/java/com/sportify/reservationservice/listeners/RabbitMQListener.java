package com.sportify.reservationservice.listeners;

import com.sportify.reservationservice.configuration.RabbitMQConfig;
import com.sportify.reservationservice.queuemessages.OrderCreatedMessage;
import com.sportify.reservationservice.services.OrderService;
import org.sportify.JsonConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JsonConverter jsonConverter;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receiveMessage(String message) {

        OrderCreatedMessage orderCreatedMessage = jsonConverter.convertFromJson(message, OrderCreatedMessage.class);

        if (orderCreatedMessage != null)
            orderService.completeOrder(orderCreatedMessage.getOrderId(), orderCreatedMessage.getUserId(),
                    orderCreatedMessage.getAmount());

    }
}
