package com.sportify.reservationapi.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE = "order-queue";
    public static final String ERROR_QUEUE = "error-queue";
    public static final String DLX_EXCHANGE = "dlx-exchange";

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .build();
    }

    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable(ERROR_QUEUE).build();
    }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Binding errorQueueBinding() {
        return BindingBuilder.bind(errorQueue()).to(dlxExchange()).with(ERROR_QUEUE);
    }
}