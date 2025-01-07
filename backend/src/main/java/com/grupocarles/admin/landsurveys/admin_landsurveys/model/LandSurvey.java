package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "land_survey")
public class LandSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column (updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    private String address;

    private Boolean corner;
    private Boolean unworkable = false;
    private Boolean title;
    private String titleSituation;
    private String measurements;
    private BigDecimal surface;
    private LocalDateTime priceVerificationDate;
    private LocalDateTime reassessmentDate;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isRescinded = false;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isArchived = false;
    private Long price;

    //FOREIGN KEYS
    
    @ManyToOne
    @JoinColumn(name = "surveyor_id", nullable = false)
    private UserSec surveyor;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private UserSec manager;

    @ManyToOne
    @JoinColumn(name = "file_type", nullable = false)
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "road_type", nullable = false)
    private RoadType roadType;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "source", nullable = false)
    private Source source;

    @ManyToOne
    @JoinColumn(name = "classification", nullable = false)
    private Classification classification;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "particular_id")
    private Particular particular;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @OneToMany(mappedBy = "landSurvey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Assessment> assessmentList = new ArrayList<>();

    public void addAssessment(Assessment assessment) {
        assessmentList.add(assessment);
        assessment.setLandSurvey(this);
    }
}
