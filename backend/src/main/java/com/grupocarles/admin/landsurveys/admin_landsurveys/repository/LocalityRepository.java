package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Locality;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long>{
    Optional<Locality> findByName(String locality);
}
