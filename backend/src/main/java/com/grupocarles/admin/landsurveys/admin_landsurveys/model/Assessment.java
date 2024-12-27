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
@Table(name = "assessment")
public class Assessment {

    @EmbeddedId
    private AssessmentId id;

    @ManyToOne
    @JoinColumn(name = "landsurvey_id")
    @MapsId("landSurveyId")
    @JsonBackReference
    private LandSurvey landSurvey;

    @ManyToOne
    @JoinColumn(name = "assessor_id")
    @MapsId("assessorId")
    private UserSec assessor;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
}