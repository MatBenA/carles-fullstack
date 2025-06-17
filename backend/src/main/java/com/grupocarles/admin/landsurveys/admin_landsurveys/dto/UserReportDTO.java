package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

public record UserReportDTO(
        SelectOptionDTO user,
        Long totalOwners,
        Long totalAgencies,
        Long totalReds,
        Long total) {
}
