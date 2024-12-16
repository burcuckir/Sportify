package com.sportify.reservationservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    SCHEDULE_NOT_AVAILABLE("Schedule not available","ERR100"),
    SCHEDULE_EXPIRED("Schedule expired","ERR101"),
    BASKET_NOT_FOUND("Basket not found","ERR200"),
    BASKET_ITEM_NOT_FOUND("Basket Item not found","ERR201"),
    FACILITY_NOT_FOUND("Facility not found","ERR300");

    private final String MESSAGE;
    private final String CODE;

    public static String getMessageWithCode(String code){
        return Objects.requireNonNull(Arrays.stream(ErrorMessages.values()).filter(m ->
                m.getCODE().equals(code)).findFirst().orElse(null)).getMESSAGE();
    }
}
