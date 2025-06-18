package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;


import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.UserDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserSec;

public interface UserService {

    UserDTO createUser(UserSec userSec);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(long id);

    UserSec updateUser(String email, UserDTO user);

    String encryptPassword(String password);

    UserSec findUserByEmail(String email);

    SelectOptionDTO userToOption(UserSec userSec);

    List<SelectOptionDTO> getAllUsersAsOptions();

    UserDTO convertToDTO(UserSec user);

    int deleteUserByEmail(String email);

    Boolean toggleEnabled(String email);

    UserDTO updatePassword(String email, String newPassword);
}
