package com.sportify.userapi.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDetailResponse {

    private UUID id;
    private String username;
    private String email;
}
