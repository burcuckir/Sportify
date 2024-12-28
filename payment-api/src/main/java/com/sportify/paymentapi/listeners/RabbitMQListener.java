package com.sportify.paymentapi.listeners;

import com.sportify.paymentapi.configuration.RabbitMQConfig;
import com.sportify.paymentapi.queuemessages.OrderFailedMessage;
import com.sportify.paymentapi.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.sportify.jsonconverter.JsonConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_FAILED_QUEUE)
    public void receiveMessage(String message) {
        OrderFailedMessage orderFailedMessage = JsonConverter.convertFromJson(message, OrderFailedMessage.class);

        if (orderFailedMessage != null)
            paymentService.refund(orderFailedMessage);

    }
}
