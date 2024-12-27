package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(@NotBlank String email, @NotBlank String password) {
}
