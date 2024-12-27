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

    @Override
    public List<LandSurveyDTO> notificationBySetting(String selectedSetting) {

        String settingValue = settingRepository
                .findById(selectedSetting)
                .orElseThrow(() -> new NullPointerException("Setting " + selectedSetting + " not found"))
                .getValue();

        Integer settingDouble = Integer.parseInt(settingValue);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -settingDouble);
        LocalDateTime cutoffDate = LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
        return landSurveyRepository.findNotificationsDays(cutoffDate).stream().map(landsurvey -> landSurveyService.adaptToDTO(landsurvey)).toList();
    }
}
