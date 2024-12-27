package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.AssessmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Assessment;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, AssessmentId> {

}
