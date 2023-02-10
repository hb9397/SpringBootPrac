package com.example.demo.entity;

import com.example.demo.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_table")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String name;

    private String description;

    private String img;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String githubUrl;
    private String blogUrl;

    private String refreshToken;
}
