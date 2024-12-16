package com.sportify.paymentservice.infrastructure;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQMessageService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private JsonConverter jsonConverter;

    @Autowired
    public RabbitMQMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public <T> void sendMessage(String queueName, T message) {
        try {
            var jsonMessage = jsonConverter.convertToJson(message);

            if (jsonMessage != null)
                rabbitTemplate.convertAndSend(queueName, jsonMessage);

        } catch (Exception e) {
            throw e;
        }
    }
}
