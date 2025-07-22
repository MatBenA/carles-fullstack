package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.StatisticDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.LandSurveyRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.LocalityRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SettingRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.StatisticRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.AssessmentService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.CurrencyService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class StatisticServiceImp implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private LandSurveyRepository landSurveyRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private CurrencyService currencyService;

    @Override
    public List<StatisticDTO> getAllAsDTO() {
        return statisticRepository.findAllByOrderByCreationDateDesc()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public StatisticDTO generateReport() {

        Currency usdCurrency = currencyService.getAllCurrencies().getLast();

        List<LandSurvey> landSurveyList = landSurveyRepository.findAll();

        long expensiveFlag = Long.parseLong(settingRepository.findById("businessEvaluationExpFlag")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        long cheapFlag = Long.parseLong(settingRepository.findById("businessEvaluationCheapFlag")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        long statisticTopLimit = Long.parseLong(settingRepository.findById("statisticTopLimit")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        long statisticBottomLimit = Long.parseLong(settingRepository.findById("statisticBottomLimit")
                .orElseThrow(() -> new NullPointerException("No value present"))
                .getValue());

        Long averageValue = landSurveyList.stream()
                .map(landSurvey -> {
                    if ("ARS".equals(landSurvey.getCurrency().getCode())){
                        landSurvey.setPrice((long)(landSurvey.getPrice() / usdCurrency.getExchangeReference()));
                    }
                    return landSurvey.getPrice();
                })
                .reduce(0L, Long::sum) / landSurveyRepository.count();

        Long totalCarles = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getAgency() != null)
                .filter(landSurvey -> landSurvey.getAgency().getName().toUpperCase().contains("CARLÉS TERRENOS"))
                .count();

        Long totalAgencies = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getAgency() != null)
                .filter(landSurvey -> !landSurvey.getAgency().getName().toUpperCase().contains("CARLÉS TERRENOS"))
                .filter(landSurvey -> !landSurvey.getAgency().getName().toUpperCase().startsWith("PROP"))
                .count();

        Long totalOwner = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getAgency() != null)
                .filter(landSurvey -> landSurvey.getAgency().getName().toUpperCase().contains("PROPIETARIO"))
                .count();

        Long totalCarlesRed = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getAgency() != null)
                .filter(landSurvey -> landSurvey.getAgency().getName().toUpperCase().contains("CARLÉS TERRENOS"))
                .mapToDouble(landSurvey -> {
                    Long avgAssessment = assessmentService.averageAssessment(landSurvey);
                    if (avgAssessment <= 0) return -1; // Marca para filtrar después

                    // Cálculo exacto como en SQL: (ls.price / (AVG(amt.price) * 0.95) - 1) * 100
                    return (landSurvey.getPrice().doubleValue() / (avgAssessment.doubleValue() * 0.95) - 1) * 100;
                })
                .filter(calculation -> calculation > expensiveFlag && calculation != -1)
                .count();

        Long totalReds = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getAgency() != null)
                .filter(landSurvey -> !landSurvey.getAgency().getName().toUpperCase().contains("CARLÉS TERRENOS"))
                .mapToDouble(landSurvey -> {
                    Long avgAssessment = assessmentService.averageAssessment(landSurvey);
                    if (avgAssessment <= 0) return -1; // Marca para filtrar después

                    // Cálculo exacto como en SQL: (ls.price / (AVG(amt.price) * 0.95) - 1) * 100
                    return (landSurvey.getPrice().doubleValue() / (avgAssessment.doubleValue() * 0.95) - 1) * 100;
                })
                .filter(calculation -> calculation > expensiveFlag && calculation != -1)
                .count();

        Long totalNormals = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getAgency() != null)
                .mapToDouble(landSurvey -> {
                    Long avgAssessment = assessmentService.averageAssessment(landSurvey);
                    if (avgAssessment <= 0) return -1; // Marca para filtrar después

                    // Cálculo exacto como en SQL: (ls.price / (AVG(amt.price) * 0.95) - 1) * 100
                    return (landSurvey.getPrice().doubleValue() / (avgAssessment.doubleValue() * 0.95) - 1) * 100;
                })
                .filter(calculation -> calculation <= expensiveFlag && calculation != -1)
                .filter(calculation -> calculation >= cheapFlag && calculation != -1)
                .count();

        Long totalGreens = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getAgency() != null)
                .mapToDouble(landSurvey -> {
                    Long avgAssessment = assessmentService.averageAssessment(landSurvey);
                    if (avgAssessment <= 0) return -1; // Marca para filtrar después

                    // Cálculo exacto como en SQL: (ls.price / (AVG(amt.price) * 0.95) - 1) * 100
                    return (landSurvey.getPrice().doubleValue() / (avgAssessment.doubleValue() * 0.95) - 1) * 100;
                })
                .filter(calculation -> calculation < cheapFlag && calculation != -1)
                .count();

        Long belowMinLimit = landSurveyList.stream()
                .filter(landSurvey ->
                        ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) < statisticBottomLimit)
                .count();

        Long belowMaxLimit = landSurveyList.stream()
                .filter(landSurvey ->
                        ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) >= statisticBottomLimit &&
                        ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) <= statisticTopLimit)
                .count();

        Long overLimits = landSurveyList.stream()
                .filter(landSurvey ->
                        ChronoUnit.DAYS.between(landSurvey.getPriceVerificationDate().toLocalDate(), LocalDate.now()) > statisticTopLimit)
                .count();

        Long withTitle = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getTitle() != null)
                .filter(landSurvey -> landSurvey.getTitle().getSituation() != null)
                .filter(landSurvey -> landSurvey.getTitle().getSituation().startsWith("Si"))
                .count();

        Long totalUnworkable = landSurveyList.stream()
                .filter(landSurvey -> landSurvey.getUnworkable() != null)
                .filter(LandSurvey::getUnworkable)
                .count();


        StatisticReport statisticReport = StatisticReport.builder()
                .totalLandSurveys(landSurveyRepository.count())
                .averageValue(averageValue)
                .totalCarles(totalCarles)
                .totalAgencies(totalAgencies)
                .totalOwners(totalOwner)
                .totalCarlesRed(totalCarlesRed)
                .totalReds(totalReds)
                .totalNormals(totalNormals)
                .totalGreens(totalGreens)
                .belowMinLimit(belowMinLimit)
                .belowMaxLimit(belowMaxLimit)
                .overLimits(overLimits)
                .withTitle(withTitle)
                .totalUnworkable(totalUnworkable)
                .build();

        for(Locality locality : localityRepository.findAll()){
            statisticReport.addLocalityStatistic(
                    LocalityStatistic
                            .builder()
                            .id(new LocalityStatisticId(locality.getId(), statisticReport.getId()))
                            .locality(locality)
                            .total(landSurveyList.stream().filter(ls -> ls.getLocality() == locality).count())
                            .build()
            );
        }

        return toDTO(statisticRepository.save(statisticReport));
    }

    @Override
    public StatisticDTO toDTO(StatisticReport statisticReport) {
        LocalityStatistic posadas = statisticReport
                .getLocalityStatisticList().stream()
                .filter(locality -> Objects.equals(locality.getLocality().getName(), "POSADAS"))
                .findFirst()
                .orElseThrow();

        LocalityStatistic garupa = statisticReport
                .getLocalityStatisticList().stream()
                .filter(locality -> Objects.equals(locality.getLocality().getName(), "GARUPÁ"))
                .findFirst()
                .orElseThrow();

        LocalityStatistic candelaria = statisticReport
                .getLocalityStatisticList().stream()
                .filter(locality -> Objects.equals(locality.getLocality().getName(), "CANDELARIA"))
                .findFirst()
                .orElseThrow();


        return new StatisticDTO(
                statisticReport.getId(),
                statisticReport.getCreationDate(),
                statisticReport.getTotalLandSurveys(),
                statisticReport.getAverageValue(),
                statisticReport.getTotalCarles(),
                statisticReport.getTotalAgencies(),
                statisticReport.getTotalOwners(),
                statisticReport.getTotalCarlesRed(),
                statisticReport.getTotalReds(),
                statisticReport.getTotalNormals(),
                statisticReport.getTotalGreens(),
                statisticReport.getBelowMinLimit(),
                statisticReport.getBelowMaxLimit(),
                statisticReport.getOverLimits(),
                statisticReport.getWithTitle(),
                statisticReport.getTotalUnworkable(),
                posadas.getTotal(),
                garupa.getTotal(),
                candelaria.getTotal());
    }
}
