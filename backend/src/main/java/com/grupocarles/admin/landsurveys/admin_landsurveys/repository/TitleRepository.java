package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
    Optional<Title> findBySituation(String situation);
}
