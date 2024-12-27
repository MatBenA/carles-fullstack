package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Classification;

public interface ClassificationService {
    Classification createClassification(Classification classification);

    List<Classification> getAllClassifications();

    Classification getClassificationById(long id);

    Classification updateClassification(long id, Classification newClassification);

    void deleteClassification(long id);
}