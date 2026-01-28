package com.enesincekara.rabbitmq.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record LoginModel(
        UUID authId,
        LocalDateTime loginDate,
        String ipAddress
) implements Serializable {
}
