package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Role;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.RoleRepository;

@Service
public class RoleServiceImp implements RoleService {
    
    @Autowired
    private RoleRepository repository;

    @Override
    public Role createRole(Role role) {
        return repository.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> getRoles() {
        return repository.findAll();
    }

    @Override
    public Set<Role> findRoleList(Set<String> nameList) {
        return repository.findByNameIn(nameList);
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        return repository.findByName(roleName);
    }

}
