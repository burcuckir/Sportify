package com.sportify.userservice.exceptions;

import com.sportify.userservice.enums.ErrorMessages;

public class PhoneAlreadyExistException extends RuntimeException {
    public PhoneAlreadyExistException() {
        super(ErrorMessages.PHONE_ALREADY_EXIST.getCODE());
    }
}
