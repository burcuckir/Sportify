package com.sportify.reservationapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    SCHEDULE_NOT_AVAILABLE("Schedule not available","ERR100"),
    SCHEDULE_EXPIRED("Schedule expired","ERR101"),
    BASKET_NOT_FOUND("Basket not found","ERR200"),
    BASKET_ITEM_NOT_FOUND("Basket Item not found","ERR201"),
    BASKET_AMOUNT_NOT_MATCHED("Basket amount not matched","ERR202");

    private final String MESSAGE;
    private final String CODE;
}
