package com.enesincekara.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationModel(
        UUID authId,
        String title,
        String message,
        LocalDateTime timestamp
) implements Serializable {}