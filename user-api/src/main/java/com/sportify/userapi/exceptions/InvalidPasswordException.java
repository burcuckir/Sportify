package com.sportify.userapi.exceptions;

import com.sportify.userapi.enums.ErrorMessages;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(ErrorMessages.INVALID_PASSWORD.getCODE());
    }
}
