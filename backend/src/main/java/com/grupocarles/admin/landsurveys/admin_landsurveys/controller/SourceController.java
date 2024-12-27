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

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Source;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.SourceService;


@RestController
@RequestMapping("/sources")
public class SourceController {

    @Autowired
    private SourceService service;

    @PostMapping("/create")
    public Source createSource(@RequestBody Source source) {
        return service.createSource(source);
    }

    @GetMapping
    public List<Source> getAllAgencies() {
        return service.getAllSources();
    }

    @GetMapping("/{id}")
    public Source getSourceById(@PathVariable long id) {
        return service.getSourceById(id);
    }

    @PutMapping("/update/{id}")
    public Source updateSource(@PathVariable long id, @RequestBody Source newSource){
        return service.updateSource(id, newSource);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteSource(@PathVariable long id){
        service.deleteSource(id);
        return "Source deleted successfully";
    }
}


