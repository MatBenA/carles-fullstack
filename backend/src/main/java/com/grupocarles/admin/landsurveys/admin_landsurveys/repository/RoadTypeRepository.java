package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.RoadType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoadTypeRepository extends JpaRepository<RoadType, Long>{
    Optional<RoadType> findByName(String s);
}
