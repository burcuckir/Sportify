package com.sportify.userapi.services;


import com.sportify.userapi.entities.User;
import com.sportify.userapi.exceptions.*;
import com.sportify.userapi.mappers.UserMapper;
import com.sportify.userapi.models.request.LoginRequest;
import com.sportify.userapi.models.request.RegisterRequest;
import com.sportify.userapi.models.request.UpdatePasswordRequest;
import com.sportify.userapi.models.response.UpdatedPasswordResponse;
import com.sportify.userapi.models.response.UserDetailResponse;
import com.sportify.userapi.models.response.UserLoginResponse;
import com.sportify.userapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.sportify.hashing.PasswordSecurityUtil;
import org.sportify.jwt.JwtModel;
import org.sportify.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public UserDetailResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
            throw new UserAlreadyExistException();
        }

        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new EmailAlreadyExistException();
        }

        if (userRepository.findByPhone(registerRequest.getPhoneNumber()) != null) {
            throw new PhoneAlreadyExistException();
        }

        User user = UserMapper.mapToUser(registerRequest);
        userRepository.save(user);

        return UserMapper.mapToUserDetailResponse(user);
    }

    public UserLoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null)
            throw new UserNotFoundException();

        if (!PasswordSecurityUtil.checkPassword(request.getPassword(), user.getPassword()))
            throw new InvalidPasswordException();

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getId());
        return UserMapper.mapToUserLoginResponse(user, token);
    }

    public UserDetailResponse getUserDetail(UUID id) {
        User user = userRepository.findById(id);
        if (user == null)
            throw new UserNotFoundException();

        return UserMapper.mapToUserDetailResponse(user);
    }

    public UpdatedPasswordResponse updatePassword(JwtModel jwtModel, UpdatePasswordRequest request) {
        User user = userRepository.findByUsername(jwtModel.getUsername());
        if (user == null)
            throw new UserNotFoundException();

        if (!user.getPassword().equals(PasswordSecurityUtil.hashPassword(request.getPassword())))
            throw new InvalidPasswordException();

        if (request.getPassword().equals(request.getNewPassword()))
            throw new SamePasswordErrorException();

        userRepository.updatePasswordByUsername(jwtModel.getUsername(), PasswordSecurityUtil.hashPassword(request.getNewPassword()));
        return UserMapper.mapToUpdatedPasswordResponse();
    }
}