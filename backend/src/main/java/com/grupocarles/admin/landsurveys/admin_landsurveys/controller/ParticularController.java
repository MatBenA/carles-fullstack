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

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Particular;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ParticularService;

@RestController
@RequestMapping("/particulars")
public class ParticularController {

    @Autowired
    private ParticularService service;

    @PostMapping("/create")
    public Particular createParticular(@RequestBody Particular particular) {
        return service.createParticular(particular);
    }

    @GetMapping
    public List<Particular> getAllParticulars() {
        return service.getAllParticulars();
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getAllParticularOptions() {
        return service.getAllParticularsAsOptions();
    }

    @GetMapping("/{id}")
    public Particular getParticularById(@PathVariable long id) {
        return service.getParticularById(id);
    }

    @PutMapping("/update/{id}")
    public Particular updateParticular(@PathVariable long id, @RequestBody Particular newParticular) {
        return service.updateParticular(id, newParticular);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteParticular(@PathVariable long id) {
        service.deleteParticular(id);
        return "Particular deleted successfully";
    }
}
