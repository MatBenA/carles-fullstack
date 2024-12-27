package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UserDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Role;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDTO createUser(UserSec userSec) {
        UserSec newUser = repository.save(userSec);
        return convertToDTO(newUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public UserDTO getUserById(long id) {
        return convertToDTO(repository.findById(id).orElse(null));
    }

    @Override
    public UserSec updateUser(String email, UserDTO user) {
        UserSec updatedUser = this.findUserByEmail(email);

        updatedUser.setFirstName(user.firstName());
        updatedUser.setLastName(user.lastName());
        updatedUser.setEmail(user.email());
        updatedUser.setRoleSet(roleService.findRoleList(user.roles()));

        return repository.save(updatedUser);
    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public UserSec findUserByEmail(String email) {
        return repository.findUserEntityByEmail(email).orElse(null);
    }

    @Override
    public SelectOptionDTO userToOption(UserSec userSec) {
        return new SelectOptionDTO(
                userSec.getEmail(),
                userSec.getLastName() + ", " + userSec.getFirstName());
    }

    @Override
    public List<SelectOptionDTO> getAllUsersAsOptions() {
        return repository.findAll()
                .stream()
                .map(user -> new SelectOptionDTO(user.getEmail(), user.getLastName() + ", " + user.getFirstName()))
                .toList();
    }

    public UserDTO convertToDTO(UserSec user){
        Set<String> roles = user.getRoleSet().stream().map(Role::getName).collect(Collectors.toSet());

        return new UserDTO(user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        roles);
    }
}
