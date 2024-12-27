package com.sportify.paymentapi.exceptions;

import com.sportify.paymentapi.enums.ErrorMessages;

public class BasketNotFoundException extends RuntimeException{

    public BasketNotFoundException(){super(ErrorMessages.BASKET_NOT_FOUND.getCODE());}
}
