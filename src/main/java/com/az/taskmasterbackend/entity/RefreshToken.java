package com.az.taskmasterbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(nullable = false)
    private boolean revoked;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshToken() {}

    public RefreshToken(String refreshToken, Date expirationDate, User user) {
        this.token = refreshToken;
        this.expirationDate = expirationDate;
        this.user = user;
    }


}
