package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.LandSurveyDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LandSurveyService {
    LandSurveyDTO createLandSurvey(LandSurveyDTO landSurvey);

    List<LandSurveyDTO> getAllLandSurveys();

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
                                          Boolean title,
                                          int pageNumber);

    LandSurveyDTO getLandSurveyById(Long id);

    LandSurveyDTO updateLandSurvey(Long id, LandSurveyDTO newLandSurveyDTO);

    String deleteLandSurvey(Long id);

    Page<LandSurveyDTO> getPaginatedLandSurveys(Pageable pageable);

    LandSurveyDTO adaptToDTO(LandSurvey landSurvey);

    Boolean switchRescind(Long id);

    List<LandSurveyDTO> getRescinds();

    LandSurvey saveSecure(LandSurvey landSurvey);
}