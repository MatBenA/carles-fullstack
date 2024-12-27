package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.AssessmentDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Assessment;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.AssessmentId;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;

public interface AssessmentService {

    AssessmentDTO createAssessment(Assessment assessment);

    List<AssessmentDTO> getAllAssessments();

    AssessmentDTO getAssessmentById(AssessmentId id);

    Assessment updateAssessment(AssessmentId id, Assessment newAssessment);

    void deleteAssessment(AssessmentId id);

    AssessmentDTO adaptToDTO(Assessment assessment);

    Assessment convertFromDTO(AssessmentDTO assessmentDTO, LandSurvey landSurveyOwner);

    Long averageAssessment(LandSurvey landSurvey);
}