package com.enesincekara.dto.response;

import com.enesincekara.entity.enums.EStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ComplaintResponse(
        String id,
        String title,
        String description,
        String category,
        EStatus status,
        String trackingNumber,
        LocalDateTime createdAt,
        List<String> imageUrls

) {
}
