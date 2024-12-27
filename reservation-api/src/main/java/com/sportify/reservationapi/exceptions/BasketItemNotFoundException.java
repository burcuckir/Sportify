package com.sportify.reservationapi.exceptions;

import com.sportify.reservationapi.enums.ErrorMessages;

public class BasketItemNotFoundException extends RuntimeException{
    public BasketItemNotFoundException(){super(ErrorMessages.BASKET_ITEM_NOT_FOUND.getCODE());}
}
