package com.sportify.reservationapi.exceptions;

import com.sportify.reservationapi.enums.ErrorMessages;

public class BasketAmountNotMatchedException extends RuntimeException {

    public BasketAmountNotMatchedException() {
        super(ErrorMessages.BASKET_AMOUNT_NOT_MATCHED.getCODE());
    }
}
