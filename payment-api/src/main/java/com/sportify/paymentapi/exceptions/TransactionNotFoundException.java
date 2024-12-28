package com.sportify.paymentapi.exceptions;

import com.sportify.paymentapi.enums.ErrorMessages;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(){super(ErrorMessages.BASKET_NOT_FOUND.getCODE());}
}
