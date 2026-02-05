package com.enesincekara.dto.response;

import com.enesincekara.entity.enums.EStatus;

public record RegionalDensityResponse(
        String category,
        Long count,
        EStatus status
) {}
