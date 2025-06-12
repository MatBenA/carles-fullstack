package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.StatisticReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticReport, Long> {
    List<StatisticReport> findAllByOrderByCreationDateDesc();
}
