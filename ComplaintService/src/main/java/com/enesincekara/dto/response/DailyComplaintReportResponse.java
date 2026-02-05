package com.enesincekara.dto.response;

import java.time.LocalDate;

public record DailyComplaintReportResponse(
        LocalDate date,
        Long totalComplaints,
        Long resolvedComplaints
) {
}
