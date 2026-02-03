package com.enesincekara.entity;

import com.enesincekara.entity.enums.EStatus;
import com.enesincekara.exception.BaseException;
import com.enesincekara.exception.ErrorType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Complaint {

    @Id
    private String id;
    private UUID authId;// Şikayeti yapan vatandaşın kimliği
    private String title;          // Şikayet başlığı [cite: 64]
    private String description;    // Detaylı açıklama [cite: 62]
    private String category;       // Yol, Su, Park, Temizlik vb. [cite: 64]
    private List<String> imageUrls; // Fotoğraf ve video linkleri [cite: 65]
    private String location;       // Konum bazlı raporlama (Enlem/Boylam) [cite: 66]
    private EStatus status;        // Şikayetin güncel durumu
    private String trackingNumber; // Vatandaşın sorgulama yapabileceği numara [cite: 67]
    private LocalDateTime createdAt;


    public static Complaint create(UUID authId,String title,
                                   String description, String category,
                                   List<String> imageUrls, String location) {
        validate(title,description);

        return Complaint.builder()
                .id(UUID.randomUUID().toString())
                .authId(authId)
                .title(title)
                .description(description)
                .category(category)
                .imageUrls(imageUrls)
                .location(location)
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
