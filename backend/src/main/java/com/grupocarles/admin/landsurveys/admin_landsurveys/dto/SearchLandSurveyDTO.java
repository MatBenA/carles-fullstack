package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

public record SearchLandSurveyDTO(Integer minPrice,
                                  Integer maxPrice,
                                  String businessEvaluation,
                                  String classification,
                                  Boolean title) {
}
