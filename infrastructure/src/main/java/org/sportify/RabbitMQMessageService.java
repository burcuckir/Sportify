package org.sportify;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMQMessageService {

    private final RabbitTemplate rabbitTemplate;

    public <T> void sendMessage(String queueName, T message) {
        try {
            var jsonMessage = JsonConverter.convertToJson(message);

            if (jsonMessage != null)
                rabbitTemplate.convertAndSend(queueName, jsonMessage);

        } catch (Exception e) {
            throw e;
        }
    }
}
