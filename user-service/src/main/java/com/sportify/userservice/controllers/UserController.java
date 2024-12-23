package com.sportify.userservice.controllers;


import com.sportify.userservice.enums.ErrorMessages;
import com.sportify.userservice.models.request.LoginRequest;
import com.sportify.userservice.models.request.RegisterRequest;
import com.sportify.userservice.models.request.UpdatePasswordRequest;
import com.sportify.userservice.models.response.UpdatedPasswordResponse;
import com.sportify.userservice.models.response.UserDetailResponse;
import com.sportify.userservice.models.response.UserLoginResponse;
import com.sportify.userservice.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.sportify.BaseController;
import org.sportify.jwt.annotations.JwtAuthenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<UserDetailResponse> register(@Valid @RequestBody RegisterRequest user) {
        UserDetailResponse createdUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody LoginRequest request) {
        UserLoginResponse userLogin =userService.login(request);
        if (userLogin != null) {
            return ResponseEntity.ok(userLogin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> getUserById(@PathVariable UUID id) {
        UserDetailResponse userDetail = userService.getUserDetail(id);
        if (userDetail != null) {
            return ResponseEntity.ok(userDetail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/password")
    @JwtAuthenticated
    public ResponseEntity<UpdatedPasswordResponse> updatePassword(HttpServletRequest request, @Valid @RequestBody UpdatePasswordRequest requestBody) {
        UpdatedPasswordResponse updatedPasswordResponse = userService.updatePassword(getJwtModel(), requestBody);

        if (updatedPasswordResponse != null) {
            return ResponseEntity.ok(updatedPasswordResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}