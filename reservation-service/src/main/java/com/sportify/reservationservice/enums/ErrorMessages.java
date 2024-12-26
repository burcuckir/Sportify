package com.sportify.reservationservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    SCHEDULE_NOT_AVAILABLE("Schedule not available","ERR100"),
    SCHEDULE_EXPIRED("Schedule expired","ERR101"),
    BASKET_NOT_FOUND("Basket not found","ERR200"),
    BASKET_ITEM_NOT_FOUND("Basket Item not found","ERR201");

    private final String MESSAGE;
    private final String CODE;
}
