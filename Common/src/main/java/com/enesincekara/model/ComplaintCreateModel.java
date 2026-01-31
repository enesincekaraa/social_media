package com.enesincekara.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ComplaintCreateModel(
        String complaintId,
        UUID authId,
        String title,
        String category,
        LocalDateTime createdAt
) implements Serializable {
}
