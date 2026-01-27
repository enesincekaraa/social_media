package com.enesincekara.dto.request;

import java.util.UUID;

public record UserCreateRequestDto(
        UUID authId,
        String username,
        String email
) {
}
