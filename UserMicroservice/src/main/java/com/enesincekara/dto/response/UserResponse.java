package com.enesincekara.dto.response;

public record UserResponse(
        String username,
        String email,
        String phone,
        String avatar,
        String bio
) {
}
