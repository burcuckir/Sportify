package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super(ErrorMessages.EMAIL_ALREADY_EXIST.getCODE());
    }
}
