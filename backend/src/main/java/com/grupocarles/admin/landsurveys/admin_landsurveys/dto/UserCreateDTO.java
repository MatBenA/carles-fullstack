package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

public record UserCreateDTO(String firstName,
                            String lastName,
                            String email,
                            String role,
                            String password) {
}
