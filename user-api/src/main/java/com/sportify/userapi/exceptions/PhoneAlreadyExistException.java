package com.sportify.userapi.exceptions;

import com.sportify.userapi.enums.ErrorMessages;

public class PhoneAlreadyExistException extends RuntimeException {
    public PhoneAlreadyExistException() {
        super(ErrorMessages.PHONE_ALREADY_EXIST.getCODE());
    }
}
