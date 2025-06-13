package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    Long overBottomLimit;

    Long overMidLimit;

    Long overTopLimit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserSec user;

    @ManyToOne
    @JoinColumn(nullable=false)
    private UpdateReport updateReport;
}
