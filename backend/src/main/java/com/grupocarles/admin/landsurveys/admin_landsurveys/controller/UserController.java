package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UserCreateDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UserDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Role;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserSec;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public UserDTO getUserUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO user){
        Set<Role> roleSet = new HashSet<>();

        roleService.findByName(user.role()).ifPresent(roleSet::add);

        if (roleSet.isEmpty()) return ResponseEntity.badRequest().build();

        UserSec newUSer = new UserSec();
        newUSer.setFirstName(user.firstName());
        newUSer.setLastName(user.lastName());
        newUSer.setEmail(user.email());
        newUSer.setRoleSet(roleSet);
        newUSer.setPassword(userService.encryptPassword(user.password()));
        return ResponseEntity.ok(userService.createUser(newUSer));
    }

    @PutMapping("/update/{email}")
    public UserSec updateUser(@PathVariable String email, @RequestBody UserDTO user){
        return userService.updateUser(email, user);
    }

    @GetMapping("/options")
    public List<SelectOptionDTO> getUserOptions() {
        return userService.getAllUsersAsOptions();
    }

    @DeleteMapping("/delete/{email}")
    public int deleteUserByEmail(@PathVariable String email){
        return userService.deleteUserByEmail(email);
    }

    @PatchMapping("/toggle-enable/{email}")
    public Boolean toggleEnabled(@PathVariable String email) {
        return userService.toggleEnabled(email);
    }
}
