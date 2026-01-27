package com.enesincekara.entity;

import com.enesincekara.dto.request.RegisterRequestDto;
import com.enesincekara.service.PasswordService;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Auth {
    @Id
    private UUID id;
    String username;
    String password;
    String email;
    Boolean isActive;
    LocalDateTime createdAt;

    public static Auth create(RegisterRequestDto req, PasswordService passwordService) {

        if (!req.password().equals(req.repassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        Auth auth = new Auth();
        auth.id = UUID.randomUUID();
        auth.username = req.username();
        auth.password = passwordService.encode(req.password());
        auth.email = req.email();
        auth.isActive = true;
        auth.createdAt = LocalDateTime.now();
        return auth;
    }

    public void activate() {
        this.isActive = true;
    }
    public void deactivate() {
        this.isActive = false;
    }

    public void updateActivate(Boolean activeParam) {
        this.isActive = (activeParam == null) ? true : activeParam;
    }

    public void update(String username, String email){
        this.username = username;
        this.email = email;
    }

    public void changePassword(String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        this.password = newPassword;
    }

    public Boolean validateLogin(String rawPassword, PasswordService passwordService) {
       if (!this.isActive){
           throw  new IllegalArgumentException("Inactive user");
       }
       if (!passwordService.matches(rawPassword,this.password)){
           throw  new IllegalArgumentException("Passwords do not match");
       }
       return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auth auth)) return false;
        return id != null && id.equals(auth.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
