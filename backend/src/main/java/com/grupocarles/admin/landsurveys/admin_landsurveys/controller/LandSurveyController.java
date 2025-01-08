package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.HomeDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.LandSurveyDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.CurrencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.LandSurveyService;

import java.util.List;


@RestController
@RequestMapping("/land-surveys")
//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class LandSurveyController {

    @Autowired
    private LandSurveyService landSurveyService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @PostMapping("/create")
    public LandSurveyDTO createLandSurvey(@RequestBody LandSurveyDTO landSurvey) {
        return landSurveyService.createLandSurvey(landSurvey);
    }

    @GetMapping
    public HomeDTO searchLandSurveys(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String businessEvaluation,
            @RequestParam(required = false) String section,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String agency,
            @RequestParam(required = false) String particular,
            @RequestParam(required = false) String classification,
            @RequestParam(required = false) String managerEmail,
            @RequestParam(required = false) Boolean title,
            @RequestParam(required = false) Boolean rescinded,
            @RequestParam(defaultValue = "0") int page
    ) {

        Page<LandSurveyDTO> landSurveyFilteredPage = landSurveyService.searchLandSurveys(
                minPrice,
                maxPrice,
                address,
                businessEvaluation,
                section,
                zone,
                agency,
                particular,
                classification,
                managerEmail,
                title,
                rescinded,
                page
        );

        return new HomeDTO(
                landSurveyFilteredPage.getContent(),
                landSurveyFilteredPage.getTotalPages(),
                landSurveyFilteredPage.getTotalElements(),
                currencyRepository.findByCode("USD")
                        .orElseThrow(EntityNotFoundException::new)
                        .getExchangeReference()
        );
    }

    @GetMapping("/{id}")
    public LandSurveyDTO getLandSurveyById(@PathVariable long id) {
        return landSurveyService.getLandSurveyById(id);
    }

    @PutMapping("/update/{id}")
    public LandSurveyDTO updateLandSurvey(@PathVariable long id, @RequestBody LandSurveyDTO landSurvey){
        LandSurveyDTO response = landSurveyService.updateLandSurvey(id, landSurvey);
        return response;
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteLandSurvey(@PathVariable long id){
        return landSurveyService.deleteLandSurvey(id);
    }

    @PatchMapping("/rescind/{id}")
    public Boolean switchRescindLandSurvey(@PathVariable Long id){
        return landSurveyService.switchRescind(id);
    }
}
