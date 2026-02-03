package com.enesincekara.rabbitmq.model;

import java.io.Serializable;
import java.util.UUID;

public record RegisterModel(
        UUID authId,
        String username,
        String phone,
        String email
) implements Serializable {
}
