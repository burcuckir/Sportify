package com.sportify.paymentservice.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayResponse {
    private Boolean isSuccess;
    private String message;
}
