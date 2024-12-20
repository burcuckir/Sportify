package com.sportify.paymentservice.configuration;

import com.sportify.paymentservice.enums.ErrorMessages;
import org.sportify.ErrorMessagesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ErrorMessageConfig {
    @Bean
    public ErrorMessagesProvider errorMessagesProvider() {
        return code ->
            Arrays.stream(ErrorMessages.values())
                    .filter(m -> m.getCODE().equals(code))
                    .findFirst()
                    .map(ErrorMessages::getMESSAGE)
                    .orElse("Unknown error code");
    }
}
