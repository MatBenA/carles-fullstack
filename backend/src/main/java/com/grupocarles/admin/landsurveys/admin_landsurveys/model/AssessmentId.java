package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Embeddable
public class AssessmentId implements Serializable{
    @Column(name = "assessor_id")
    private long assessorId;

    @Column(name = "landsurvey_id")
    private long landSurveyId;    
}
