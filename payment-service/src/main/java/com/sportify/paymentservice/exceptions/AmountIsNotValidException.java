package com.sportify.paymentservice.exceptions;


import com.sportify.paymentservice.enums.ErrorMessages;

public class AmountIsNotValidException extends RuntimeException{
    public AmountIsNotValidException(){super(ErrorMessages.AMOUNT_NOT_VALID.getCODE());}
}