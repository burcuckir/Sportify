package com.sportify.paymentapi.client.bankapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankPayResponse {
    private Boolean isSuccess;
    private String message;
}