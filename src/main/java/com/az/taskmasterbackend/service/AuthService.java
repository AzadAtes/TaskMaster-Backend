package com.az.taskmasterbackend.service;

import com.az.taskmasterbackend.dto.AuthRequest;
import com.az.taskmasterbackend.dto.AuthResponse;
import com.az.taskmasterbackend.dto.ErrorResponse;
import com.az.taskmasterbackend.dto.RefreshTokenRequest;
import com.az.taskmasterbackend.entity.RefreshToken;
import com.az.taskmasterbackend.entity.User;
import com.az.taskmasterbackend.repository.RefreshTokenRepository;
import com.az.taskmasterbackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
//    private CustomUserDetailsService customUserDetailsService;
//    private ObjectMapper objectMapper;
    private RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<?> login(AuthRequest authRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

            System.out.println(authRequest.password());
            User userDetails = (User) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(authentication);

            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            RefreshToken refreshTokenEntity = new RefreshToken(
                    refreshToken,
                    Date.from(Instant.now().plusMillis(jwtUtil.getRefreshExpirationInMs())),
                    userDetails
            );

            refreshTokenRepository.save(refreshTokenEntity);

            return ResponseEntity.ok(new AuthResponse(jwt, refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(""));
        }
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.refreshToken();
            Optional<RefreshToken> refreshTokenFromDb = refreshTokenRepository.findByToken(refreshToken);
            if (refreshTokenFromDb.isEmpty() || refreshTokenFromDb.get().getExpirationDate().before(new Date()) || refreshTokenFromDb.get().isRevoked()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid refresh token"));
            }

            RefreshToken validRefreshToken = refreshTokenFromDb.get();
            User userDetails = validRefreshToken.getUser();

            String newJwt = jwtUtil.generateToken(new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()));

            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            validRefreshToken.setToken(newRefreshToken);
            validRefreshToken.setExpirationDate(Date.from(Instant.now().plusMillis(jwtUtil.getRefreshExpirationInMs())));

            refreshTokenRepository.save(validRefreshToken);

            return ResponseEntity.ok(new AuthResponse(newJwt, newRefreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid refresh token"));
        }
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
}
