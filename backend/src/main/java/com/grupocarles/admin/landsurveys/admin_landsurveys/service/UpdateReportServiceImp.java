package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UpdateReportDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UpdateReport;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserSec;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateReportServiceImp implements UpdateReportService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UpdateReportDTO generate() {
        UpdateReport updateReport = new UpdateReport();
        
        return null;
    }
}
