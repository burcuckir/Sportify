package com.sportify.userservice.services;


import com.sportify.userservice.entities.User;
import com.sportify.userservice.exceptions.*;
import com.sportify.userservice.mappers.UserMapper;
import com.sportify.userservice.models.request.LoginRequest;
import com.sportify.userservice.models.request.RegisterRequest;
import com.sportify.userservice.models.request.UpdatePasswordRequest;
import com.sportify.userservice.models.response.UpdatedPasswordResponse;
import com.sportify.userservice.models.response.UserDetailResponse;
import com.sportify.userservice.models.response.UserLoginResponse;
import com.sportify.userservice.repositories.UserRepository;
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