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

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Classification;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ClassificationService;


@RestController
@RequestMapping("/classifications")
public class ClassificationController {

    @Autowired
    private ClassificationService service;

    @PostMapping("/create")
    public Classification createClassification(@RequestBody Classification classification) {
        return service.createClassification(classification);
    }

    @GetMapping
    public List<Classification> getAllAgencies() {
        return service.getAllClassifications();
    }

    @GetMapping("/{id}")
    public Classification getClassificationById(@PathVariable long id) {
        return service.getClassificationById(id);
    }

    @PutMapping("/update/{id}")
    public Classification updateClassification(@PathVariable long id, @RequestBody Classification newClassification){
        return service.updateClassification(id, newClassification);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteClassification(@PathVariable long id){
        service.deleteClassification(id);
        return "Classification deleted successfully";
    }
}

