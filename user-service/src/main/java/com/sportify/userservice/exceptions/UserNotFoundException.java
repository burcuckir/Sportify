package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND.getCODE());
    }
}
