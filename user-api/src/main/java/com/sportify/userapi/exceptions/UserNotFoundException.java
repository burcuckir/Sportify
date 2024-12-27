package com.sportify.userapi.exceptions;

import com.sportify.userapi.enums.ErrorMessages;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND.getCODE());
    }
}
