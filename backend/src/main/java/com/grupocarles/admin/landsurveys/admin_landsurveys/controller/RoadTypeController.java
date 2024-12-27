package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.RoadType;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.RoadTypeService;


@RestController
@RequestMapping("/road-types")
public class RoadTypeController {

    @Autowired
    private RoadTypeService service;

    @PostMapping("/create")
    public RoadType createRoadType(@RequestBody RoadType roadType) {
        return service.createRoadType(roadType);
    }

    @GetMapping
    public List<RoadType> getAllAgencies() {
        return service.getAllRoadTypes();
    }

    @GetMapping("/{id}")
    public RoadType getRoadTypeById(@PathVariable long id) {
        return service.getRoadTypeById(id);
    }

    @PutMapping("/update/{id}")
    public RoadType updateRoadType(@PathVariable long id, @RequestBody RoadType newRoadType){
        return service.updateRoadType(id, newRoadType);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteRoadType(@PathVariable long id){
        service.deleteRoadType(id);
        return "RoadType deleted successfully";
    }
}


