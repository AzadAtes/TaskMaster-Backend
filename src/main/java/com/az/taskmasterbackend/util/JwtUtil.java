package com.az.taskmasterbackend.util;

import com.az.taskmasterbackend.exception.InvalidTokenException;
import com.az.taskmasterbackend.exception.MissingFieldsException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms}")
    private Long accessTokenExpirationInMs;

    @Getter
    @Value("${security.jwt.refresh-expiration-ms}")
    private Long refreshTokenExpirationInMs;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keys = Base64.getDecoder().decode(secret.getBytes());
        key = Keys.hmacShaKeyFor(keys);
    }

    public String generateAccessToken(Authentication authentication) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessTokenExpirationInMs);

        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(now)
                .expiration(expirationDate)
                .claims(Map.of("roles", authentication
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String username) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) throws InvalidTokenException {

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getTokenFromAuthHeader(String authHeader) {

        if (!authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Token invalid or missing.");
        }
        return authHeader.substring(7);
    }
}
