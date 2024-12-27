package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Section;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.SectionService;


@RestController
@RequestMapping("/sections")
public class SectionController {

    @Autowired
    private SectionService service;

    @PostMapping("/create")
    public Section createSection(@RequestBody Section section) {
        return service.createSection(section);
    }

    @GetMapping
    @ResponseBody
    public List<Section> getAllSections() {
        return service.getAllSections();
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getAllSectionOptions() {
        return service.getAllSectionsAsOptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable long id) {
        return ResponseEntity.ok(service.getSectionById(id));
    }

    @PutMapping("/update/{id}")
    public Section updateSection(@PathVariable long id, @RequestBody Section newSection){
        return service.updateSection(id, newSection);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteSection(@PathVariable long id){
        service.deleteSection(id);
        return "Section deleted successfully";
    }
}
