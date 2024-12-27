package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class LocalityStatistic {

    @EmbeddedId
    private LocalityStatisticId id;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    @MapsId("localityId")
    private Locality locality;

    @ManyToOne
    @JoinColumn(name = "statistic_report_id")
    @MapsId("statisticReportId")
    @JsonBackReference
    private StatisticReport statisticReport;

    Long total;
}
