package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UpdateReportDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.UpdateReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/update-reports")
public class UpdateReportController {

    @Autowired
    UpdateReportService updateReportService;

    @PostMapping("/create")
    public UpdateReportDTO generateUpdateReport(){
        return updateReportService.generate();
    }

    @GetMapping
    public List<UpdateReportDTO> gerUpdateReportList(){
        return updateReportService.getUpdateReportList();
    }
}
