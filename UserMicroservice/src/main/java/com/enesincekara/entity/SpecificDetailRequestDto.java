package com.enesincekara.entity;

public record SpecificDetailRequestDto(
        EsnafDetail esnafDetail,
        UstaDetail ustaDetail,
        YasliDetail yasliDetail,
        SurucuDetail surucuDetail
) {
}
