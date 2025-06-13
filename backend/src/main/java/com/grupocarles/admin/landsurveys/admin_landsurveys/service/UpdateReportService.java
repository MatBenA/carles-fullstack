package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UpdateReportDTO;

import java.util.List;

public interface UpdateReportService {
    UpdateReportDTO generate();

    List<UpdateReportDTO> getUpdateReportList();
}
