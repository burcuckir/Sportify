package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(){
        super(ErrorMessages.EXPIRED_TOKEN.getCODE());
    }
}
