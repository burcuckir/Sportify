package com.sportify.userservice.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserLoginResponse {

    private UUID id;
    private String token;
}

