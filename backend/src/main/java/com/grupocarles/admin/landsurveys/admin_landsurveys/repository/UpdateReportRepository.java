package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UpdateReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateReportRepository extends JpaRepository<UpdateReport, Long> {
}
