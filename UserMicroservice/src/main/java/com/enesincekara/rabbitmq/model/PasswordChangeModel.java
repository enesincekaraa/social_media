package com.enesincekara.rabbitmq.model;

import java.io.Serializable;
import java.util.UUID;

public record PasswordChangeModel(
        UUID authId,
        String newPassword
) implements Serializable {
}
