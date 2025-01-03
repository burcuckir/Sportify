package com.sportify.reservationapi.listeners;

import com.sportify.reservationapi.configuration.RabbitMQConfig;
import com.sportify.reservationapi.queuemessages.PaymentCompletedMessage;
import com.sportify.reservationapi.services.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.sportify.jsonconverter.JsonConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RabbitMQListener {

    private final OrderServiceImpl orderServiceImpl;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void receiveMessage(String message) {
        PaymentCompletedMessage paymentCompletedMessage = JsonConverter.convertFromJson(message, PaymentCompletedMessage.class);

        if (paymentCompletedMessage != null)
            orderServiceImpl.completeOrder( paymentCompletedMessage.getOrderId(),
                    paymentCompletedMessage.getUserId(), paymentCompletedMessage.getAmount());
    }
}
