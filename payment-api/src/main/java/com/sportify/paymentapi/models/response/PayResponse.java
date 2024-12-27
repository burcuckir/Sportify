package com.sportify.paymentapi.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayResponse {
    private Boolean isSuccess;
    private String message;
}
