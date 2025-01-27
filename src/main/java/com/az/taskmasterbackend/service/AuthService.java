package com.az.taskmasterbackend.service;

import com.az.taskmasterbackend.exception.MissingFieldsException;
import com.az.taskmasterbackend.exception.InvalidTokenException;
import com.az.taskmasterbackend.model.dto.AuthRequest;
import com.az.taskmasterbackend.model.dto.AuthResponse;
import com.az.taskmasterbackend.model.dto.ErrorResponse;
import com.az.taskmasterbackend.model.dto.RefreshTokenRequest;
import com.az.taskmasterbackend.model.entity.RefreshToken;
import com.az.taskmasterbackend.model.entity.User;
import com.az.taskmasterbackend.repository.RefreshTokenRepository;
import com.az.taskmasterbackend.repository.UserRepository;
import com.az.taskmasterbackend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

    //TODO: create user dto and return it on successful registration and logout
    //      OR simply return a message
    public ResponseEntity<?> logout(String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                var token = authHeader.substring(7);
                var username = jwtUtil.getUsernameFromToken(token);

                var refreshTokens = refreshTokenRepository.findAllByUser_Username(username);
                for (RefreshToken refreshToken : refreshTokens) {
                    refreshToken.setRevoked(true);
                }
                refreshTokenRepository.saveAll(refreshTokens);
                return ResponseEntity.ok("Logged out successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Token invalid or missing."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Logout failed."));
        }
    }

    public ResponseEntity<?> register(AuthRequest authRequest) {
        if (authRequest.email() == null || authRequest.password() == null) {
            throw new MissingFieldsException("Registration failed.");
        }
        var user = new User();
        user.setUsername(authRequest.email());
        user.setPassword(passwordEncoder.encode(authRequest.password()));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User already exists: " + ex));
        }
        return ResponseEntity.ok(user);
    }
}
