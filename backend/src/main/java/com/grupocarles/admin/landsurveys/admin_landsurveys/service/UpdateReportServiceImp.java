package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UpdateReportDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UpdateReport;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserReport;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserSec;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateReportServiceImp implements UpdateReportService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UpdateReportRepository updateReportRepository;

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private LandSurveyRepository landSurveyRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public UpdateReportDTO generate() {
        UpdateReport updateReport = new UpdateReport();

        List<UserSec> activeUserList = userRepository.findByEnabledTrue();
        List<UserReport> userReportList = new ArrayList<>();
        List<LandSurvey> landSurveyList = landSurveyRepository.findAll();

        long statisticTopLimit = Long.parseLong(settingRepository.findById("statisticTopLimit")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        long statisticBottomLimit = Long.parseLong(settingRepository.findById("statisticBottomLimit")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        for (UserSec user : activeUserList){

            UserReport userReport = new UserReport();
            Long overBottomLimit = landSurveyList.stream()
                    .filter(landSurvey ->
                            ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) >= statisticBottomLimit &&
                                    ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) <= statisticTopLimit)
                    .count();

            Long overMidLimit = 0L;
            Long overTopLimit = 0L;

            userReport.setUser(user);
            userReport.setOverBottomLimit(overBottomLimit);
            userReport.setOverMidLimit(overMidLimit);
            userReport.setOverTopLimit(overTopLimit);

            userReportList.add(userReport);
        }

        updateReport.setUserReportList(userReportList);
        return null;
    }

    @Override
    public List<UpdateReportDTO> getUpdateReportList() {
        return null;
    }
}
