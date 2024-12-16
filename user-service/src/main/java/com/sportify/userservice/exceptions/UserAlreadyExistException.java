package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(){
        super(ErrorMessages.USER_ALREADY_EXIST.getCODE());
    }
}
