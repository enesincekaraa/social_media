package com.enesincekara.entity;



import com.enesincekara.entity.enums.ERole;
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
    private ERole role;
    private String avatar;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginDate;
    private String lastLoginIp;

    private EsnafDetail esnafDetail;   // Sadece ESNAF rolündekiler için [cite: 32]
    private UstaDetail ustaDetail;     // Sadece USTA rolündekiler için [cite: 70]
    private YasliDetail yasliDetail;   // Sadece YASLI rolündekiler için [cite: 18]
    private SurucuDetail surucuDetail; // Sadece SURUCU rolündekiler için [cite: 44]

    private Integer complaintCount = 0;


    public static User createProfile(UUID authId, String username, String email,ERole role) {
        return User.builder()
                .authId(authId)
                .username(username)
                .email(email)
                .role(role)
                .createdAt(LocalDateTime.now())
                .avatar("default-avatar.png")
                .bio("Eynesil Dijital Platformu Kullanıcısı")
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


    public void updateEsnafDetail(EsnafDetail esnafDetail) {
        if (this.role != ERole.ESNAF) {
            throw new IllegalArgumentException("Esnaf details can only be updated once per role");
        }
        this.esnafDetail = esnafDetail;
    }
    public void updateYasliDetail(YasliDetail yasliDetail) {
        if (this.role != ERole.YASLI) {
            throw new IllegalArgumentException("Yasli details can only be updated once per role");
        }
        this.yasliDetail = yasliDetail;
    }
    public void updateUstaDetail(UstaDetail ustaDetail) {
        if (this.role != ERole.USTA) {
            throw new IllegalArgumentException("Usta details can only be updated once per role");
        }
        this.ustaDetail = ustaDetail;
    }
    public void updateSurucuDetail(SurucuDetail surucuDetail) {
        if (this.role != ERole.SURUCU) {
            throw new IllegalArgumentException("Surucu details can only be updated once per role");
        }
        this.surucuDetail = surucuDetail;
    }

    public void incrementComplaintCount() {
        if (this.complaintCount ==null) {
            this.complaintCount = 0;
        }
        this.complaintCount++;
    }

}
