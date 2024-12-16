package com.sportify.userservice.models.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private String code;
    private String message;
}
