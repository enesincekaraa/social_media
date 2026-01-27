package com.enesincekara.dto.request;

public record RegisterRequestDto(
        String username,
        String password,
        String repassword,
        String email
) {
}
