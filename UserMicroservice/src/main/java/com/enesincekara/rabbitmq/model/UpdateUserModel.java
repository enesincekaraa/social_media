package com.enesincekara.rabbitmq.model;

import java.io.Serializable;
import java.util.UUID;

public record UpdateUserModel(
        UUID authId,
        String username,
        String email
) implements Serializable {
}
