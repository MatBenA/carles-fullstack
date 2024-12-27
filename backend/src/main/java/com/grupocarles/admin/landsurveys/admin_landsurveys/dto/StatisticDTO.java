package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import java.time.LocalDateTime;

public record StatisticDTO(
        Long id,
        LocalDateTime creationDate,
        Long totalLandSurveys,
        Long averageValue,
        Long totalCarles,
        Long totalAgencies,
        Long totalOwners,
        Long totalCarlesRed,
        Long totalReds,
        Long totalNormals,
        Long totalGreens,
        Long belowMinLimit,
        Long belowMaxLimit,
        Long overLimits,
        Long WithTitle,
        Long totalUnworkable,
        Long posadasTotal,
        Long garupaTotal,
        Long candelariaTotal

) { }
