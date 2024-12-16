package com.sportify.userservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    USER_NOT_FOUND("User not found.", "ERR100"),
    INVALID_PASSWORD("Invalid password.","ERR101"),
    USER_ALREADY_EXIST("User already exist.","ERR102"),
    SAME_PASSWORD_ERROR("Same Password can not be.","ERR103"),
    TOKEN_NOT_VALIDATED("Token Not validated.","ERR200"),
    UNAUTHORIZED("Unauthorized.","ERR201"),
    INVALID_TOKEN("Invalid token.","ERR202"),
    EXPIRED_TOKEN("Expired token.","ERR203");


    private final String MESSAGE;
    private final String CODE;

    public static String getMessageWithCode(String code){
        return Objects.requireNonNull(Arrays.stream(ErrorMessages.values()).filter(m ->
                m.getCODE().equals(code)).findFirst().orElse(null)).getMESSAGE();
    }
}
