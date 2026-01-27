package com.enesincekara.rabbitmq.model;

import java.io.Serializable;
import java.util.UUID;

public record SoftDeleteModel(
        UUID id
) implements Serializable {
}
