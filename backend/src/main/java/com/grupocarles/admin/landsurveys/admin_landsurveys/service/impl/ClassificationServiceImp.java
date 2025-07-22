package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Classification;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.ClassificationRepository;

@Service
public class ClassificationServiceImp implements ClassificationService {

    @Autowired
    private ClassificationRepository repository;

    @Override
    public Classification createClassification(Classification classification) {
        return repository.save(classification);
    }

    @Override
    public void deleteClassification(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public Classification getClassificationById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Classification> getAllClassifications() {
        return repository.findAll();
    }

    @Override
    public Classification updateClassification(long id, Classification newClassification) {
        Classification updateClassification = repository.findById(id).orElse(null);

        updateClassification.setName(newClassification.getName());

        return repository.save(updateClassification);
    }
}


