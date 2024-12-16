package com.sportify.reservationservice.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRequest {

    private String cardNumber;
    private String cardHolderName;
    private String expireDate;
    private String cvv;
}
