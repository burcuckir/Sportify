package com.sportify.userapi.exceptions;

import com.sportify.userapi.enums.ErrorMessages;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super(ErrorMessages.EMAIL_ALREADY_EXIST.getCODE());
    }
}
