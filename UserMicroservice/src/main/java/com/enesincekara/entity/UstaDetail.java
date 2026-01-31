package com.enesincekara.entity;

import java.util.List;

public record UstaDetail(
        String profession,
        List<String> portfolioUrls,
        Double hourlyRate
) {
}
