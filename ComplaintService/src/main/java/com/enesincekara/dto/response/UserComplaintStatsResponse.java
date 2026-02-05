package com.enesincekara.dto.response;

import java.util.UUID;

public record UserComplaintStatsResponse(
        UUID authId,
        Long totalComplaints,
        Long resolvedComplaints,
        double reliabilityRate
) {
}
