package com.enesincekara.dto.request;

public record UpdateRequestDto(
        String username,
        String email,
        String phone
) {
}
