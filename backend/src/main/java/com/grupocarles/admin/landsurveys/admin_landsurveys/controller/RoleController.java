package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Permission;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Role;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Set<Permission> permissionSet = new HashSet<>();

        for (Permission permission : role.getPermissionSet()){
            permissionService.findById(permission.getId())
                    .ifPresent(permissionSet::add);
        }

        role.setPermissionSet(permissionSet);

        return ResponseEntity.ok(roleService.createRole(role));
    }

    @GetMapping
    public List<Role> getRoles(){
        return roleService.getRoles();
    }
}
