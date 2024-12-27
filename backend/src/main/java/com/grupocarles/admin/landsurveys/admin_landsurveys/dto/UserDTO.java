package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import java.util.List;
import java.util.Set;

public record UserDTO(String firstName,
                      String lastName,
                      String email,
                      Set<String> roles) {
}