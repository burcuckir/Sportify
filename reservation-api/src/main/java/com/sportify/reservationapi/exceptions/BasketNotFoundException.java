package com.sportify.reservationapi.exceptions;

import com.sportify.reservationapi.enums.ErrorMessages;

public class BasketNotFoundException extends RuntimeException{

    public BasketNotFoundException(){super(ErrorMessages.BASKET_NOT_FOUND.getCODE());}
}
