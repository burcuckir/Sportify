package com.sportify.reservationservice.exceptions;

import com.sportify.reservationservice.enums.ErrorMessages;

public class BasketItemNotFoundException extends RuntimeException{
    public BasketItemNotFoundException(){super(ErrorMessages.BASKET_ITEM_NOT_FOUND.getCODE());}
}
