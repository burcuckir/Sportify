package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class SamePasswordErrorException  extends RuntimeException{

    public SamePasswordErrorException(){
        super(ErrorMessages.SAME_PASSWORD_ERROR.getCODE());
    }
}