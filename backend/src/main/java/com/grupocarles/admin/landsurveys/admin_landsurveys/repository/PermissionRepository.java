package com.grupocarles.admin.landsurveys.admin_landsurveys.repository;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
