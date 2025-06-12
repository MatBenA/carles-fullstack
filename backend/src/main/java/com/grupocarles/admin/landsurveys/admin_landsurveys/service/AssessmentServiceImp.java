package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;
import java.util.Objects;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.AssessmentDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.CurrencyRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SettingRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.AssessmentRepository;

@Service
public class AssessmentServiceImp implements AssessmentService {
    @Autowired
    private AssessmentRepository repository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public AssessmentDTO createAssessment(Assessment assessment) {
        return adaptToDTO(repository.save(assessment));
    }

    @Override
    public void deleteAssessment(AssessmentId assessmentId) {
        repository.deleteById(assessmentId);
    }

    @Override
    public AssessmentDTO getAssessmentById(AssessmentId id) {
        return adaptToDTO(Objects.requireNonNull(repository.findById(id).orElse(null)));
    }

    @Override
    public List<AssessmentDTO> getAllAssessments() {
        return repository.findAll().stream().map(this::adaptToDTO).toList();
    }

    @Override
    public Assessment updateAssessment(AssessmentId id, Assessment newAssessment) {
        Assessment updateAssessment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Assessment with ID " + id + " does not exist"));
        updateAssessment.setLandSurvey(newAssessment.getLandSurvey());
        updateAssessment.setAssessor(newAssessment.getAssessor());
        updateAssessment.setPrice(newAssessment.getPrice());

        return repository.save(updateAssessment);
    }

    @Override
    public AssessmentDTO adaptToDTO(Assessment assessment){

        assert assessment != null;
        return new AssessmentDTO(
                userService.userToOption(assessment.getAssessor()),
                (assessment.getPrice()),
                assessment.getCurrency().getCode());
    }

    @Override
    public Assessment convertFromDTO(AssessmentDTO assessmentDTO, LandSurvey landSurveyOwner) {
        UserSec assessor = userRepository.findUserEntityByEmail(assessmentDTO.assessor().value()).orElseThrow(EntityNotFoundException::new);

        return Assessment.builder()
                .id(new AssessmentId(assessor.getId(), landSurveyOwner.getId()))
                .landSurvey(landSurveyOwner)
                .assessor(assessor)
                .price(assessmentDTO.price())
                .currency(currencyRepository.findByCode(assessmentDTO.currency()).orElseThrow(EntityNotFoundException::new))
                .build();
    }

    @Override
    public Long averageAssessment(LandSurvey landSurvey) {
        return Math.round(
                landSurvey.getAssessmentList()
                        .stream()
                        .mapToLong(Assessment::getPrice)
                        .average()
                        .orElse(0.0)  // Maneja lista vacía
        );
    }
}
