package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.FilterOptionsDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.AgencyService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ParticularService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.SectionService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("filters-data")
public class FiltersDataController {
    //section, zone, provider y particular

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private ParticularService particularService;

    @GetMapping
    public FilterOptionsDTO getFiltersData(){
        return new FilterOptionsDTO(sectionService.getAllSections(),
                zoneService.getAllZones(),
                agencyService.getAllAgencies(),
                particularService.getAllParticulars()
        );
    }
}
