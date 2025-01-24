package com.az.taskmasterbackend.service;

import com.az.taskmasterbackend.model.dto.AuthRequest;
import com.az.taskmasterbackend.model.dto.AuthResponse;
import com.az.taskmasterbackend.model.dto.ErrorResponse;
import com.az.taskmasterbackend.model.dto.RefreshTokenRequest;
import com.az.taskmasterbackend.model.entity.RefreshToken;
import com.az.taskmasterbackend.model.entity.User;
import com.az.taskmasterbackend.repository.RefreshTokenRepository;
import com.az.taskmasterbackend.repository.UserRepository;
import com.az.taskmasterbackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
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

        // TODO: Don't save a token to DB if already logged id
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));

        User userDetails = (User) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(authentication);

        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
        RefreshToken refreshTokenEntity = new RefreshToken(
                refreshToken,
                Date.from(Instant.now().plusMillis(jwtUtil.getRefreshExpirationInMs())),
                userDetails
        );

        refreshTokenRepository.save(refreshTokenEntity);
        return new AuthResponse(jwt, refreshToken);
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        Optional<RefreshToken> refreshTokenFromDb = refreshTokenRepository.findByToken(refreshToken);
        if (
            refreshTokenFromDb.isEmpty()
            || refreshTokenFromDb.get().getExpirationDate().before(new Date())
            || refreshTokenFromDb.get().isRevoked())
        {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        RefreshToken validRefreshToken = refreshTokenFromDb.get();
        User userDetails = validRefreshToken.getUser();

        String newJwt = jwtUtil.generateToken(new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()));

        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        validRefreshToken.setToken(newRefreshToken);
        validRefreshToken.setExpirationDate(Date.from(Instant.now().plusMillis(jwtUtil.getRefreshExpirationInMs())));

        refreshTokenRepository.save(validRefreshToken);

        return new AuthResponse(newJwt, newRefreshToken);
    }

    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        try {
            String authHeader = httpServletRequest.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwt = authHeader.substring(7);
                String username = jwtUtil.getUsernameFromToken(jwt);

                List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUser_Username(username);
                for (RefreshToken refreshToken : refreshTokens) {
                    refreshToken.setRevoked(true);
                }
                refreshTokenRepository.saveAll(refreshTokens);
            }

            return ResponseEntity.ok("Logged out successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Logout failed."));
        }
    }

    public ResponseEntity<?> register(AuthRequest authRequest) {
        try {
            User user = new User();
            user.setUsername(authRequest.email());
            user.setPassword(passwordEncoder.encode(authRequest.password()));
            user = userRepository.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User already exists"));
        }
    }
}
