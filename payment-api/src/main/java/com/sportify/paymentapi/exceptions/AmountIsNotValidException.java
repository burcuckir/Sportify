package com.sportify.paymentapi.exceptions;


import com.sportify.paymentapi.enums.ErrorMessages;

public class AmountIsNotValidException extends RuntimeException{
    public AmountIsNotValidException(){super(ErrorMessages.AMOUNT_NOT_VALID.getCODE());}
}