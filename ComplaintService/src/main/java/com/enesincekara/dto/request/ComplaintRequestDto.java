package com.enesincekara.dto.request;

import java.util.List;

public record ComplaintRequestDto(
        String title,
        String description,
        String category,
        List<String> imageUrls,
        String location


) {
}
