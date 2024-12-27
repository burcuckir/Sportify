package com.sportify.userapi.exceptions;

import com.sportify.userapi.enums.ErrorMessages;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super(ErrorMessages.USER_ALREADY_EXIST.getCODE());
    }
}
