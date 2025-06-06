package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @JoinColumn(name = "surveyor_id")
    private UserSec surveyor;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private UserSec manager;

    @ManyToOne
    @JoinColumn(name = "file_type")
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "road_type")
    private RoadType roadType;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "source")
    private Source source;

    @ManyToOne
    @JoinColumn(name = "classification")
    private Classification classification;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "particular_id", nullable = true)
    private Particular particular;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "folder_id", unique = false)
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    @OneToMany(mappedBy = "landSurvey", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Assessment> assessmentList = new ArrayList<>();

    public void addAssessment(Assessment assessment) {
        assessmentList.add(assessment);
        assessment.setLandSurvey(this);
    }
}
