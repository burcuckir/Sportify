package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(ErrorMessages.INVALID_PASSWORD.getCODE());
    }
}
