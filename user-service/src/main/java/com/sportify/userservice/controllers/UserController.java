package com.sportify.userservice.controllers;


import com.sportify.userservice.enums.ErrorMessages;
import com.sportify.userservice.infrastructure.jwt.annotations.JwtAuthenticated;
import com.sportify.userservice.models.request.LoginRequest;
import com.sportify.userservice.models.request.RegisterRequest;
import com.sportify.userservice.models.request.UpdatePasswordRequest;
import com.sportify.userservice.models.response.UpdatedPasswordResponse;
import com.sportify.userservice.models.response.UserDetailResponse;
import com.sportify.userservice.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessages.UNAUTHORIZED.getMESSAGE());
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