package com.sportify.reservationservice.exceptions;

import com.sportify.reservationservice.enums.ErrorMessages;

public class BasketNotFoundException extends RuntimeException{

    public BasketNotFoundException(){super(ErrorMessages.BASKET_NOT_FOUND.getCODE());}
}
