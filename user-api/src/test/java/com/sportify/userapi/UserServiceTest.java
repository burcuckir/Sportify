package com.sportify.userapi;

import com.sportify.userapi.entities.User;
import com.sportify.userapi.exceptions.*;
import com.sportify.userapi.models.request.LoginRequest;
import com.sportify.userapi.models.request.RegisterRequest;
import com.sportify.userapi.models.request.UpdatePasswordRequest;
import com.sportify.userapi.models.response.UpdatedPasswordResponse;
import com.sportify.userapi.models.response.UserDetailResponse;
import com.sportify.userapi.models.response.UserLoginResponse;
import com.sportify.userapi.repositories.UserRepository;
import com.sportify.userapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sportify.hashing.PasswordSecurityUtil;
import org.sportify.jwt.JwtModel;
import org.sportify.jwt.JwtTokenProvider;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService;

    @Test
    void register_shouldThrowException_whenUsernameExists() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("mockUser");

        when(userRepository.findByUsername("mockUser")).thenReturn(new User());

        assertThrows(UserAlreadyExistException.class, () -> userService.register(registerRequest));
    }

    @Test
    void register_shouldReturnUserDetailResponse_whenUserRegisteredSuccessfully() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newUser");
        registerRequest.setPassword("123456");
        registerRequest.setBirthday(LocalDate.now());
        registerRequest.setEmail("newuser@gmail.com");
        registerRequest.setPhoneNumber("1234567890");

        when(userRepository.findByUsername("newUser")).thenReturn(null);
        when(userRepository.findByEmail("newuser@gmail.com")).thenReturn(null);
        when(userRepository.findByPhone("1234567890")).thenReturn(null);

        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDetailResponse response = userService.register(registerRequest);

        assertNotNull(response);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setUsername("nonExistentUser");

        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.login(request));
    }

    @Test
    void login_shouldThrowException_whenPasswordInvalid() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("wrongPassword");

        User user = new User();
        user.setUsername("user");
        user.setPassword("hashedPassword");

        when(userRepository.findByUsername("user")).thenReturn(user);

        assertThrows(InvalidPasswordException.class, () -> userService.login(request));
    }

    @Test
    void login_shouldReturnUserLoginResponse_whenCredentialsValid() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("correctPassword");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("user");
        user.setPassword(PasswordSecurityUtil.hashPassword("correctPassword"));

        when(userRepository.findByUsername("user")).thenReturn(user);
        when(jwtTokenProvider.createToken("user", user.getId())).thenReturn("jwtToken");

        UserLoginResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    void updatePassword_shouldThrowException_whenUserNotFound() {
        JwtModel jwtModel = new JwtModel();
        jwtModel.setUsername("nonExistentUser");
        UpdatePasswordRequest request = new UpdatePasswordRequest();

        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.updatePassword(jwtModel, request));
    }

    @Test
    void updatePassword_shouldThrowException_whenNewPasswordSameAsOld() {
        JwtModel jwtModel = new JwtModel();
        jwtModel.setUsername("user");
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setPassword("hashedPassword");
        request.setNewPassword("hashedPassword");

        User user = new User();
        user.setPassword(PasswordSecurityUtil.hashPassword("hashedPassword"));

        when(userRepository.findByUsername("user")).thenReturn(user);

        assertThrows(SamePasswordErrorException.class, () -> userService.updatePassword(jwtModel, request));
    }

    @Test
    void updatePassword_shouldUpdatePassword_whenValidRequest() {
        JwtModel jwtModel = new JwtModel();
        jwtModel.setUsername("user");
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setPassword("oldPassword");
        request.setNewPassword("newPassword");

        User user = new User();
        user.setPassword(PasswordSecurityUtil.hashPassword("oldPassword"));

        when(userRepository.findByUsername("user")).thenReturn(user);

        UpdatedPasswordResponse response = userService.updatePassword(jwtModel, request);

        assertNotNull(response);
        verify(userRepository).updatePasswordByUsername("user", PasswordSecurityUtil.hashPassword("newPassword"));
    }
}

