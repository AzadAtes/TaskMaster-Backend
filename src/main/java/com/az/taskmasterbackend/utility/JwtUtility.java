package com.az.taskmasterbackend.utility;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class JwtUtility {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtility.class);

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-ms}")
    private Long jwtExpirationInMs;

    @Value("${security.jwt.refresh-expiration-ms}")
    private Long jwtRefreshExpirationInMs;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keys = Base64.getDecoder().decode(jwtSecret.getBytes());
        key = Keys.hmacShaKeyFor(keys);
    }

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expirationDate)
                .claims(Map.of("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String username) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtRefreshExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
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

    public long getRefreshExpirationInMs() {
        return jwtRefreshExpirationInMs;
    }
}
