package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Zone;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long>{

    Optional<Zone> findByName(String zone);
}
