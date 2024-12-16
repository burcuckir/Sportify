package com.sportify.paymentservice.exceptions;

import com.sportify.paymentservice.enums.ErrorMessages;

public class BasketNotFoundException extends RuntimeException{

    public BasketNotFoundException(){super(ErrorMessages.BASKET_NOT_FOUND.getCODE());}
}
