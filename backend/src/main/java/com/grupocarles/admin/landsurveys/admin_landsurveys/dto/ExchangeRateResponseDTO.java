package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

public record ExchangeRateResponseDTO(
        Rate oficial,
        Rate blue,
        Rate oficial_euro,
        Rate blue_euro,
        String last_update
) {}