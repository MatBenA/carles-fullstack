package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UpdateReportDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UserReportDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UpdateReport;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserReport;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserSec;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.UpdateReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateReportServiceImp implements UpdateReportService {
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
        List<LandSurvey> landSurveyList = landSurveyRepository.findAll();
        landSurveyList = landSurveyList.stream().filter(landSurvey -> landSurvey.getManager() != null).toList();

        Long particularValidity = Long.parseLong(settingRepository.findById("particularValidity")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        Long agencyValidity = Long.parseLong(settingRepository.findById("agencyValidity")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        Long unworkableValidity = Long.parseLong(settingRepository.findById("unworkableValidity")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());



        for (UserSec user : activeUserList){

            UserReport userReport = new UserReport();

            //requerimientos: Propietarios  con mas de 60 dias
            Long overBottomLimit = landSurveyList.stream()
                    .filter(landSurvey -> landSurvey.getAgency() != null)
                    .filter(landSurvey -> landSurvey.getManager().getEmail().equals(user.getEmail()))
                    .filter(landSurvey -> landSurvey.getAgency().getName().toUpperCase().contains("PROPIETARIO"))
                    .filter(landSurvey ->
                            ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) > particularValidity)
                    .count();

            //Agencias (inmobiliarias e intermediarios) con mas de 120 dias
            Long overMidLimit = landSurveyList.stream()
                    .filter(landSurvey -> landSurvey.getAgency() != null)
                    .filter(landSurvey -> landSurvey.getManager().getEmail().equals(user.getEmail()))
                    .filter(landSurvey -> !landSurvey.getAgency().getName().toUpperCase().contains("PROPIETARIO"))
                    .filter(landSurvey ->
                            ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) > agencyValidity)
                    .count();

            //Unworkable true
            Long overTopLimit = landSurveyList.stream()
                    .filter(landSurvey -> landSurvey.getManager().getEmail().equals(user.getEmail()))
                    .filter(LandSurvey::getUnworkable)
                    .filter(landSurvey ->
                            ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) > unworkableValidity)
                    .count();;

            userReport.setUser(user);
            userReport.setOverBottomLimit(overBottomLimit);
            userReport.setOverMidLimit(overMidLimit);
            userReport.setOverTopLimit(overTopLimit);
            userReport.setUpdateReport(updateReport);
            updateReport.getUserReportList().add(userReport);
        }

        updateReport.setCreationDate(LocalDate.now());
        return updateReportToDTO(updateReportRepository.save(updateReport));
    }

    @Override
    public List<UpdateReportDTO> getUpdateReportList() {
        return updateReportRepository
                .findAll()
                .stream()
                .map(this::updateReportToDTO)
                .toList();
    }

    public UpdateReportDTO updateReportToDTO(UpdateReport updateReport){

        List<UserReportDTO> userReportDTOList = updateReport.getUserReportList()
                .stream()
                .map(this::userReportToDTO)
                .toList();

        return new UpdateReportDTO(updateReport.getCreationDate(), userReportDTOList);
    }

    public UserReportDTO userReportToDTO(UserReport userReport){
        return new UserReportDTO(
                new SelectOptionDTO(
                        userReport.getUser().getEmail(),
                        userReport.getUser().getFirstName()),
                userReport.getOverMidLimit(),
                userReport.getOverTopLimit(),
                userReport.getOverBottomLimit(),
                userReport.getOverMidLimit() + userReport.getOverTopLimit() + userReport.getOverBottomLimit()
        );
    }
}
