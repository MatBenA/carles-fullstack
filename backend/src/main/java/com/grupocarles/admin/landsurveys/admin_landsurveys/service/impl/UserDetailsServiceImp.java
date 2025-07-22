package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.AuthLoginRequestDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.AuthResponseDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Role;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.UserSec;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.UserRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserSec userSec = userRepository.findUserEntityByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + username + " no fue encontrado"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userSec.getRoleSet()
                        .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        userSec.getRoleSet().stream()
                .flatMap(role -> role.getPermissionSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermission())));

        return new User(
                userSec.getEmail(),
                userSec.getPassword(),
                userSec.getEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isCredentialNotExpired(),
                userSec.isAccountNotExpired(),
                authorityList
        );
    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest){

        String email = authLoginRequest.email();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        UserSec user = userRepository.findUserEntityByEmail(email).orElse(null);
        Set<Role> roleSet = user.getRoleSet();


        return new AuthResponseDTO(email, "login successful", roleSet, accessToken, true);
    }

    public Authentication authenticate(String email, String password){
        UserDetails userDetails = this.loadUserByUsername(email);

        if (userDetails == null){
            throw new BadCredentialsException("invalid email or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("invalid email or password");
        }

        return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
