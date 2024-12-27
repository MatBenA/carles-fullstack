package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class LocalityStatisticId implements Serializable {
    @Column(name = "locality_id")
    private Long localityId;

    @Column(name = "statistic_report_id")
    private Long statisticReportId;
}
