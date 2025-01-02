package com.sportify.userapi.services;

import com.sportify.userapi.models.request.LoginRequest;
import com.sportify.userapi.models.request.RegisterRequest;
import com.sportify.userapi.models.request.UpdatePasswordRequest;
import com.sportify.userapi.models.response.UpdatedPasswordResponse;
import com.sportify.userapi.models.response.UserDetailResponse;
import com.sportify.userapi.models.response.UserLoginResponse;
import org.sportify.jwt.JwtModel;

import java.util.UUID;

public interface UserService {

    UserDetailResponse register(RegisterRequest registerRequest);
    UserLoginResponse login(LoginRequest request);
    UserDetailResponse getUserDetail(UUID id);
    UpdatedPasswordResponse updatePassword(JwtModel jwtModel, UpdatePasswordRequest request);
}
