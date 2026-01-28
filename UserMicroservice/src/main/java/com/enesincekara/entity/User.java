package com.enesincekara.entity;



import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {

    @Id
    private String id;
    private UUID authId;
    private String username;
    private String email;
    private String avatar;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginDate;
    private String lastLoginIp;

    public static User createProfile(UUID authId, String username, String email) {
        return User.builder()
                .authId(authId)
                .username(username)
                .email(email)
                .createdAt(LocalDateTime.now())
                .avatar(UUID.randomUUID().toString())
                .bio(UUID.randomUUID().toString())
                .build();
    }

    public void update(String username, String email) {
       validate(username, email);
       this.username = username;
       this.email = email;
    }

    private void validate(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }

    public void updateLastLogin(LocalDateTime lastLoginDate, String ip) {
        this.lastLoginDate = lastLoginDate;
        this.lastLoginIp = ip;
    }
}
