package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.LandSurveyDTO;

import java.util.List;

public interface NotificationService {
    List<LandSurveyDTO> notificationBySetting(String selectedSetting);
}
