package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UpdateReportDTO(
        LocalDate date,
        List<UserReportDTO> reportList
        ) {}
