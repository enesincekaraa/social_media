package com.enesincekara.model;

import java.io.Serializable;
import java.util.UUID;

public record SoftDeleteModel(
        UUID id
) implements Serializable {
}
