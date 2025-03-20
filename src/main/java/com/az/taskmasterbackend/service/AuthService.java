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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(AuthRequest authRequest) {

        var username = authRequest.username();
        var password = authRequest.password();

        if (username == null || password == null) {
            throw new MissingFieldsException("Registration failed");
        }

        userRepository.findByUsername(username).ifPresent(user -> {
            throw new UserAlreadyExistsException("Registration failed");
        });

        var userEntity = new User();
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity = userRepository.save(userEntity);

        return new UserResponse(userEntity.getUsername(), "Registration successful");
    }

    public AuthResponse login(AuthRequest authRequest) {
        var username = authRequest.username();
        var password = authRequest.password();

        if (username == null || password == null) {
            throw new MissingFieldsException("Login failed");
        }

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        var accessToken = jwtUtil.generateAccessToken(authentication);

        var userDetails = (User) authentication.getPrincipal();
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
        var refreshToken = refreshTokenRequest.refreshToken();
        if (refreshToken == null) throw new MissingFieldsException("Failed to refresh Token");

        var refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Failed to refresh Token"));

        if (refreshTokenEntity.isRevoked() || !jwtUtil.isValidToken(refreshToken)) {
            throw new InvalidTokenException("Failed to refresh Token");
        }

        var userDetails = refreshTokenEntity.getUser();
        var newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        refreshTokenEntity.setToken(newRefreshToken);
        refreshTokenEntity.setExpirationDate(Date.from(Instant.now().plusMillis(jwtUtil.getRefreshTokenExpirationInMs())));
        refreshTokenRepository.save(refreshTokenEntity);

        var accessToken = jwtUtil.generateAccessToken(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );

        return new AuthResponse(accessToken, newRefreshToken);
    }


    public UserResponse logout(String authHeader) {
        var token = jwtUtil.getTokenFromAuthHeader(authHeader);
        if (token == null) throw new InvalidTokenException("Failed to logout");

        var refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Failed to logout"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
        return new UserResponse(jwtUtil.getUsernameFromToken(token), "Logout successful");
    }

    public UserResponse logoutGlobally(String authHeader) {

        String token = jwtUtil.getTokenFromAuthHeader(authHeader);
        if (token == null) throw new InvalidTokenException("Failed to logout");

        String username = jwtUtil.getUsernameFromToken(token);
        refreshTokenRepository.findAllByUser_Username(username)
                .forEach(refreshToken -> refreshToken.setRevoked(true));

        return new UserResponse(username, "Global logout successful.");
    }
}
