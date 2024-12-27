package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Particular;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticularRepository extends JpaRepository<Particular, Long>{

    Optional<Particular> findByName(String particular);
}
