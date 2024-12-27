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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Zone;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ZoneService;


@RestController
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    private ZoneService service;

    @PostMapping("/create")
    public Zone createZone(@RequestBody Zone zone) {
        return service.createZone(zone);
    }

    @GetMapping
    @ResponseBody
    public List<Zone> getAllZones() {
        return service.getAllZones();
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getZoneOptions() {
        return service.getAllZonesAsOptions();
    }

    @GetMapping("/{id}")
    public Zone getZoneById(@PathVariable long id) {
        return service.getZoneById(id);
    }

    @PutMapping("/update/{id}")
    public Zone updateZone(@PathVariable long id, @RequestBody Zone newZone){
        return service.updateZone(id, newZone);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteZone(@PathVariable long id){
        service.deleteZone(id);
        return "Zone deleted successfully";
    }
}
