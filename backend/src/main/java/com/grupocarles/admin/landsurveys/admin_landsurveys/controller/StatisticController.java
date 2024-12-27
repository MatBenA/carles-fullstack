package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.StatisticDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public List<StatisticDTO> getStatistics(){
        return statisticService.getAllAsDTO();
    }

    @PostMapping("/generate")
    public StatisticDTO generateStatisticReport() { return statisticService.generateReport(); }
}
