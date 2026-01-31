package com.enesincekara.dto.request;

import com.enesincekara.entity.enums.ERole;

public record RegisterRequestDto(
        String username,
        String password,
        String repassword,
        String email,
        ERole role
) {
}
