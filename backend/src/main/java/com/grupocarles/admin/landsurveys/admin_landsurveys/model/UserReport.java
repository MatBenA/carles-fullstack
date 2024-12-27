package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

public class UserReport {

    @EmbeddedId
    private UserReportId id;

    @ManyToOne
    @JoinColumn(name = "update_report_id")
    @MapsId("updateReport")
    private UpdateReport updateReport;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private UserSec user;

    Integer overBottomLimit;

    Integer overMidLimit;

    Integer overTopLimit;
}
