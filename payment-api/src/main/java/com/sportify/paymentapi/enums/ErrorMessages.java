package com.sportify.paymentapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages  {

    AMOUNT_NOT_VALID("Amount is not valid","ERR100"),
    BASKET_NOT_FOUND("Basket not found","ERR200");


    private final String MESSAGE;
    private final String CODE;
}
