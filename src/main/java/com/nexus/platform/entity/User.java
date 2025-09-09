package com.nexus.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatarUrl;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String bio;
    private String portfolio;
    private String preferences;
    private BigDecimal balance;
    private boolean isOnline;
}
