package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Title;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleController {
    @Autowired
    private TitleService service;

    @PostMapping("/create")
    public Title createTitleBySituation(@RequestBody String situation){
        return service.createTitleBySituation(situation);
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getTitleSituationList(){
        return service.getAllAgenciesAsOptions();
    }

    @DeleteMapping("/delete/{name}")
    public Boolean deleteTitleByName(@PathVariable String situation){
        return service.deleteTitleByName(situation);
    }
}
