package com.enesincekara.model;

import java.io.Serializable;
import java.util.UUID;

public record ComplaintUpdateNotificationModel(
        UUID authId,
        String trackingNo,
        String status
) implements Serializable {
}
