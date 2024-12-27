package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.StatisticDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.StatisticReport;

import java.util.List;

public interface StatisticService {
    List<StatisticDTO> getAllAsDTO();

    StatisticDTO toDTO(StatisticReport statisticReport);

    StatisticDTO generateReport();
}
