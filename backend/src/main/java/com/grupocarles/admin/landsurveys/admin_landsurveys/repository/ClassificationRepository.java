package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Classification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long>{

    Optional<Classification> findByName(String classification);
}
