package com.az.taskmasterbackend.service;

import com.az.taskmasterbackend.exception.MissingFieldsException;
import com.az.taskmasterbackend.exception.InvalidTokenException;
import com.az.taskmasterbackend.exception.UserAlreadyExistsException;
import com.az.taskmasterbackend.model.dto.*;
import com.az.taskmasterbackend.model.entity.RefreshToken;
import com.az.taskmasterbackend.model.entity.User;
import com.az.taskmasterbackend.repository.RefreshTokenRepository;
import com.az.taskmasterbackend.repository.UserRepository;
import com.az.taskmasterbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(AuthRequest authRequest) {

        var username = authRequest.email();
        var password = authRequest.password();

        if (username == null || password == null) {
            throw new MissingFieldsException("Registration failed.");
        }

        userRepository.findByUsername(username).ifPresent(user -> {
            throw new UserAlreadyExistsException("User already exists.");
        });

        var user = new User();
        user.setUsername(authRequest.email());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        user = userRepository.save(user);

        return new UserResponse(user.getUsername(), "Registered successfully.");
    }

    public AuthResponse login(AuthRequest authRequest) {

        if (authRequest.email() == null || authRequest.password() == null) {
            throw new MissingFieldsException("Login failed");
        }

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));

        var userDetails = (User) authentication.getPrincipal();
        var accessToken = jwtUtil.generateAccessToken(authentication);

        var refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
        var refreshTokenEntity = new RefreshToken(
                refreshToken,
                Date.from(Instant.now().plusMillis(jwtUtil.getRefreshTokenExpirationInMs())),
                userDetails
        );
        refreshTokenRepository.save(refreshTokenEntity);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        if (refreshTokenRequest.refreshToken() == null) {
            throw new MissingFieldsException("Failed to refresh Token");
        }

        Optional<RefreshToken> refreshTokenFromDb = refreshTokenRepository.findByToken(refreshTokenRequest.refreshToken());
        if ( refreshTokenFromDb.isEmpty()
                || refreshTokenFromDb.get().isRevoked()
                || !jwtUtil.isValidToken(refreshTokenRequest.refreshToken()))
        {
            throw new InvalidTokenException("Invalid refresh token");
        }

        var refreshTokenEntity = refreshTokenFromDb.get();
        var userDetails = refreshTokenEntity.getUser();
        var refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpirationDate(Date.from(Instant.now().plusMillis(jwtUtil.getRefreshTokenExpirationInMs())));
        refreshTokenRepository.save(refreshTokenEntity);

        var accessToken = jwtUtil.generateAccessToken(new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()));

        return new AuthResponse(accessToken, refreshToken);
    }

    public UserResponse logout(String authHeader) {

        var token = jwtUtil.getTokenFromAuthHeader(authHeader);
        var username = jwtUtil.getUsernameFromToken(token);
        var refreshToken = refreshTokenRepository.findByToken(token);

        if (refreshToken.isEmpty()) {
            throw new InvalidTokenException("Invalid token.");
        }
        refreshToken.get().setRevoked(true);
        refreshTokenRepository.save(refreshToken.get());

        return new UserResponse(username, "Logged out successfully.");
    }

    public UserResponse logoutGlobally(String authHeader) {

        var token = jwtUtil.getTokenFromAuthHeader(authHeader);
        var username = jwtUtil.getUsernameFromToken(token);

        var refreshTokens = refreshTokenRepository.findAllByUser_Username(username);
        for (RefreshToken refreshToken : refreshTokens) {
            refreshToken.setRevoked(true);
        }
        refreshTokenRepository.saveAll(refreshTokens);
        return new UserResponse(username, "Logged out successfully.");
    }
}
