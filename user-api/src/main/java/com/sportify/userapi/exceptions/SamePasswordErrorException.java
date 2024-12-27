package com.sportify.userapi.exceptions;

import com.sportify.userapi.enums.ErrorMessages;

public class SamePasswordErrorException  extends RuntimeException{

    public SamePasswordErrorException(){
        super(ErrorMessages.SAME_PASSWORD_ERROR.getCODE());
    }
}