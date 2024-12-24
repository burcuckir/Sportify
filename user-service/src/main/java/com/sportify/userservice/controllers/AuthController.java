package com.sportify.userservice.controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.sportify.controller.BaseController;
import org.sportify.jwt.JwtModel;
import org.sportify.jwt.annotations.JwtAuthenticated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Management", description = "Operations related to authorization")
public class AuthController extends BaseController {

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
