package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.LandSurveyDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LandSurveyService {
    LandSurveyDTO createLandSurvey(LandSurveyDTO landSurvey);

    Page<LandSurveyDTO> searchLandSurveys(Integer minPrice,
                                          Integer maxPrice,
                                          String address,
                                          String businessEvaluation,
                                          String section,
                                          String zone,
                                          String agency,
                                          String particular,
                                          String classification,
                                          String managerEmail,
                                          String surveyorEmail,
                                          String title,
                                          Boolean rescinded,
                                          String folder,
                                          int pageNumber);

    LandSurveyDTO getLandSurveyById(Long id);

    LandSurveyDTO updateLandSurvey(Long id, LandSurveyDTO newLandSurveyDTO);

    String deleteLandSurvey(Long id);

    Boolean switchRescind(Long id);

    LandSurvey saveSecure(LandSurvey landSurvey);
}