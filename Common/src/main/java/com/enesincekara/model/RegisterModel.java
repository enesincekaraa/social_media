package com.enesincekara.model;

import com.enesincekara.entity.enums.ERole;

import java.io.Serializable;
import java.util.UUID;

public record RegisterModel(
        UUID authId,
        String username,
        String email,
        ERole eRole
) implements Serializable {
}
