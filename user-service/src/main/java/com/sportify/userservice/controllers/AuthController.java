package com.sportify.userservice.controllers;

import com.sportify.userservice.infrastructure.jwt.JwtModel;
import com.sportify.userservice.infrastructure.jwt.JwtTokenProvider;
import com.sportify.userservice.infrastructure.jwt.annotations.JwtAuthenticated;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Management", description = "Operations related to authorization")
public class AuthController extends BaseController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/validate")
    @JwtAuthenticated
    public ResponseEntity<JwtModel> validate(HttpServletRequest request) {
        JwtModel jwtModel = getJwtModel();
        if (jwtModel != null) {
            return ResponseEntity.ok(jwtModel);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
