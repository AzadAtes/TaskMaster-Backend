package com.az.taskmasterbackend.controller;

import com.az.taskmasterbackend.model.dto.AuthRequest;
import com.az.taskmasterbackend.model.dto.ErrorResponse;
import com.az.taskmasterbackend.model.dto.RefreshTokenRequest;
import com.az.taskmasterbackend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        if (authRequest.email() == null || authRequest.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Registration failed: Missing fields"));
        }
        return ResponseEntity.ok(authService.register(authRequest));
    }

//    //TODO: delete old refresh tokens from db
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
//        if (authRequest.email() == null || authRequest.password() == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Login failed: Missing fields"));
//        }
//        return ResponseEntity.ok(authService.login(authRequest));
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        if (authRequest.email() == null || authRequest.password() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Login failed: Missing fields"));
        }
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        if (refreshTokenRequest.refreshToken() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Failed to refresh Token: Missing fields"));
        }
        try {
            return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        return authService.logout(httpServletRequest);
    }
}
