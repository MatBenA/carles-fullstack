package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.AssessmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Assessment;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.AssessmentId;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.AssessmentService;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService service;

    @PostMapping("/create")
    public AssessmentDTO createAssessment(@RequestBody Assessment assessment) {
        return service.createAssessment(assessment);
    }

    @GetMapping
    public List<AssessmentDTO> getAllAssessments() {
        return service.getAllAssessments();
    }

    @GetMapping("/{assessorId}/{landSurveyId}")
    public ResponseEntity<AssessmentDTO> getAssessmentById(
            @PathVariable int assessorId, 
            @PathVariable int landSurveyId) {

        AssessmentId id = new AssessmentId(assessorId, landSurveyId);
        return ResponseEntity.ok(service.getAssessmentById(id));
    }

    @PutMapping("/update/{assessorId}/{landSurveyId}")
    public ResponseEntity<Assessment> updateAssessment(
        @PathVariable int assessorId, 
        @PathVariable int landSurveyId, 
        @RequestBody Assessment newAssessment) {

        AssessmentId id = new AssessmentId(assessorId, landSurveyId);
        return ResponseEntity.ok(service.updateAssessment(id, newAssessment));
    }

    @DeleteMapping("/delete/{assessorId}/{landSurveyId}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable int assessorId, @PathVariable int landSurveyId) {
        AssessmentId id = new AssessmentId(assessorId, landSurveyId);
        service.deleteAssessment(id);
        return ResponseEntity.noContent().build();
    }
}
