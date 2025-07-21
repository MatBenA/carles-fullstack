package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByCode(Long code);
    Folder findFirstByOrderByIdDesc();

    @Query("SELECT MAX(f.code) FROM Folder f")
    Long findMaxCode();

    @Query("SELECT f FROM Folder f WHERE NOT EXISTS (SELECT ls FROM LandSurvey ls WHERE ls.folder = f)")
    List<Folder> findFolderWithoutLandSurvey();
}