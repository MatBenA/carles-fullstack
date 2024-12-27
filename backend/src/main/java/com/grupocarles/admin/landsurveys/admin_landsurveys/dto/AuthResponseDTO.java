package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Role;

import java.util.Set;

@JsonPropertyOrder({"email", "message", "accessToken", "status"})
public record AuthResponseDTO(String email, String message, Set<Role> roleSet, String accessToken, boolean status) {
}
