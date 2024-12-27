package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface LandSurveyRepository extends JpaRepository<LandSurvey, Long>, JpaSpecificationExecutor<LandSurvey> {
    long count();

    @Query("SELECT ls FROM LandSurvey ls WHERE ls.priceVerificationDate > :cutoffDate ORDER BY ls.priceVerificationDate DESC")
    List<LandSurvey> findNotificationsDays(@Param("cutoffDate") LocalDateTime cutoffDate);

    List<LandSurvey> findByIsRescinded(Boolean isRescinded);
}
