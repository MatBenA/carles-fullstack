package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class StatisticReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    private Long totalLandSurveys;
    private Long averageValue;
    private Long totalCarles;
    private Long totalAgencies;
    private Long totalOwners;
    private Long totalCarlesRed;
    private Long totalReds;
    private Long totalNormals;
    private Long totalGreens;
    private Long belowMinLimit;
    private Long belowMaxLimit;
    private Long overLimits;
    private Long withTitle;
    private Long totalUnworkable;

    @OneToMany(mappedBy = "statisticReport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<LocalityStatistic> localityStatisticList = new ArrayList<>();

    public void addLocalityStatistic(LocalityStatistic localityStatistic){
        localityStatisticList.add(localityStatistic);
        localityStatistic.setStatisticReport(this);
    }

    public void addAllLocalityStatistic(List<LocalityStatistic> localityStatistics){
        localityStatisticList.addAll(localityStatistics);
        localityStatistics.forEach(ls -> ls.setStatisticReport(this));
    }
}
