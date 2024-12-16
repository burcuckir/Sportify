package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(){
        super(ErrorMessages.INVALID_TOKEN.getCODE());
    }
}
