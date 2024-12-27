package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import jakarta.persistence.Column;

import java.io.Serializable;

public class UserReportId implements Serializable {
    @Column(name = "update_report_id")
    private Long updateReport;

    @Column(name = "user_id")
    private Long userId;
}