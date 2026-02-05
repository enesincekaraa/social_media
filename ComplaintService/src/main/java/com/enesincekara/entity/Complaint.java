package com.enesincekara.entity;

import com.enesincekara.entity.enums.EStatus;
import com.enesincekara.exception.BaseException;
import com.enesincekara.exception.ErrorType;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_complaints")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Complaint {

    @Id
    private String id;
    private UUID authId;
    private String title;

    @Column(length = 1000)
    private String description;

    private String category;

    @ElementCollection
    @CollectionTable(name = "complaint_images", joinColumns = @JoinColumn(name = "complaint_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    private String trackingNumber;
    private LocalDateTime createdAt;


    public static Complaint create(UUID authId, String title, String description,
                                   String category, List<String> imageUrls,
                                   Double lat, Double lon) {
        validate(title, description);

        return Complaint.builder()
                .id(UUID.randomUUID().toString())
                .authId(authId)
                .title(title)
                .description(description)
                .category(category)
                .imageUrls(imageUrls)
                .latitude(lat)
                .longitude(lon)
                .status(EStatus.PENDING)
                .trackingNumber("EYNS-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void updateStatus(EStatus newStatus) {
        if (this.status == EStatus.RESOLVED) {
            throw new BaseException(ErrorType.BAD_REQUEST_ERROR);
        }
        this.status = newStatus;
    }

    public static void validate(String title,String description){
        if (title == null || title.length() < 5) {
            throw new BaseException(ErrorType.BAD_REQUEST_ERROR);
        }
        if (description == null || description.isEmpty()) {
            throw new BaseException(ErrorType.BAD_REQUEST_ERROR);
        }
    }


}
