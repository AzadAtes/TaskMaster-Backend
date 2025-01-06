package com.az.taskmasterbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-ms}")
    private String jwtExpirationInMs;

    @Value("${security.jwt.refresh-expiration-ms}")
    private String jwtRefreshExpirationInMs;

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
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .setClaims(Map.of("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())))
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .setClaims(Map.of("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())))
                .compact();
    }

    public String getUsernameFromToken(String token) {

        Claims claims;
        return "";
    }
}
