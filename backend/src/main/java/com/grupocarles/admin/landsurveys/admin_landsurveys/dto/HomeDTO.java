package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import java.util.List;

public record HomeDTO(
        List<LandSurveyDTO> landSurveys,
        Integer totalPages,
        Long totalElements,
        Double dollarRate,
        Long rePricing
        ) {
}
