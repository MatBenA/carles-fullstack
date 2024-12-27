package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Agency;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.AgencyService;


@RestController
@RequestMapping("/agencies")
public class AgencyController {

    @Autowired
    private AgencyService service;

    @PostMapping("/create")
    public Agency createAgency(@RequestBody Agency agency) {
        return service.createAgency(agency);
    }

    @GetMapping
    public List<Agency> getAllAgencies() {
        return service.getAllAgencies();
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getAllAgencyOptions() {
        return service.getAllAgenciesAsOptions();
    }

    @GetMapping("/{id}")
    public Agency getAgencyById(@PathVariable long id) {
        return service.getAgencyById(id);
    }

    @PutMapping("/update/{id}")
    public Agency updateAgency(@PathVariable long id, @RequestBody Agency newAgency){
        return service.updateAgency(id, newAgency);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteAgency(@PathVariable long id){
        service.deleteAgency(id);
        return "Agency deleted successfully";
    }
}
