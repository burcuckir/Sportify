package com.sportify.paymentservice.client.bankapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankPayRequest {
    private String cardHolderName;
    private String cardNumber;
    private String expireDate;
    private String cvv;
}
