package com.enesincekara.model;

import java.util.UUID;

public record UserUpdateModel(
        UUID authId,
        String username,
        String email
) {
}
