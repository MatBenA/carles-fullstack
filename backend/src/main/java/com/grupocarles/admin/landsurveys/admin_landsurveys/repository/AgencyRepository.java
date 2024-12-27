package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Agency;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long>{

    Optional<Agency> findByName(String agency);
}
