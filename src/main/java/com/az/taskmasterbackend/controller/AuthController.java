package com.az.taskmasterbackend.controller;

import com.az.taskmasterbackend.dto.AuthRequest;
import com.az.taskmasterbackend.dto.RefreshTokenRequest;
import com.az.taskmasterbackend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> login(AuthRequest authRequest) {
        System.out.println("AUTHREQUEST:\n" + authRequest);
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> login(HttpServletRequest httpServletRequest) {
        return authService.logout(httpServletRequest);
    }
}
