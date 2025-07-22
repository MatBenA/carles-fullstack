package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.LandSurveyRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SettingRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.LandSurveyService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImp implements NotificationService {

    private final SettingRepository settingRepository;
    private final LandSurveyRepository landSurveyRepository;
    private final LandSurveyService landSurveyService;
}
