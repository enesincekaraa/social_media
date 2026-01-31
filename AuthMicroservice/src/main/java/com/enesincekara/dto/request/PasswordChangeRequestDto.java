package com.enesincekara.dto.request;

import java.util.UUID;

public record PasswordChangeRequestDto(
        UUID authId,
        String newPassword
) {
}
