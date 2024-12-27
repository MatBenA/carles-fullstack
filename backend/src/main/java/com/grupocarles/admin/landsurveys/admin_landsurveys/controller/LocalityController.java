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

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Locality;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.LocalityService;


@RestController
@RequestMapping("/localities")
public class LocalityController {

    @Autowired
    private LocalityService service;

    @PostMapping("/create")
    public Locality createLocality(@RequestBody Locality locality) {
        return service.createLocality(locality);
    }

    @GetMapping
    public List<Locality> getAllLocalities() {
        return service.getAllLocalities();
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getLocalityOptions() {
        return service.getAllLocalitiesAsOptions();
    }

    @GetMapping("/{id}")
    public Locality getLocalityById(@PathVariable long id) {
        return service.getLocalityById(id);
    }

    @PutMapping("/update/{id}")
    public Locality updateLocality(@PathVariable long id, @RequestBody Locality newLocality){
        return service.updateLocality(id, newLocality);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteLocality(@PathVariable long id){
        service.deleteLocality(id);
        return "Locality deleted successfully";
    }
}
