package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.LandSurveyDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.LandSurveyRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Service
public class NotificationServiceImp implements  NotificationService{

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private LandSurveyRepository landSurveyRepository;

    @Autowired
    private LandSurveyService landSurveyService;
}
