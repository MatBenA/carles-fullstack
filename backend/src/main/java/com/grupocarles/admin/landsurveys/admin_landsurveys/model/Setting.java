package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Setting {
    @Id
    String name;
    @Column(nullable = false)
    String value;
}
