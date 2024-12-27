package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Role;

public interface RoleService {
    Role createRole(Role role);
    Optional<Role> findById(Long id);
    List<Role> getRoles();
    Set<Role> findRoleList(Set<String> nameList);
    Optional<Role> findByName(String roleName);
}
