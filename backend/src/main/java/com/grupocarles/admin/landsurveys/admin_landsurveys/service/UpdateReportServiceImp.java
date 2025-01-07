package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UpdateReportDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UpdateReport;
import org.springframework.stereotype.Service;

@Service
public class UpdateReportServiceImp implements UpdateReportService{
    @Override
    public UpdateReportDTO generate() {
        UpdateReport updateReport = new UpdateReport();

        return null;
    }
}
