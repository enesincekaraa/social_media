package com.enesincekara.dto.response;

public record UserResponse(
        String username,
        String email,
        String avatar,
        String bio
) {
}
