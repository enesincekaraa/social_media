package com.enesincekara.dto.response;

public record CategoryPerformanceResponse(
        String category,    // c.category -> String
        Long totalCount,    // COUNT(c) -> Long
        Long resolvedCount, // SUM(CASE...) -> Long
        double successRate  // CAST(... AS Double) -> Double
) {}