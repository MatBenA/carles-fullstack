package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.LocalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Locality;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.LocalityRepository;

@Service
public class LocalityServiceImp implements LocalityService {

    @Autowired
    private LocalityRepository repository;

    @Override
    public Locality createLocality(Locality locality) {
        return repository.save(locality);
    }

    @Override
    public void deleteLocality(long id) {
        repository.deleteById(id);
    }

    @Override
    public Locality getLocalityById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Locality> getAllLocalities() {
        return repository.findAll();
    }

    @Override
    public List<SelectOptionDTO> getAllLocalitiesAsOptions() {
        return repository.findAll().stream().map(locality -> new SelectOptionDTO(locality.getId().toString(), locality.getName())).toList();
    }

    @Override
    public Locality updateLocality(long id, Locality newLocality) {
        Locality updateLocality = repository.findById(id).orElse(null);
        updateLocality.setName(newLocality.getName());

        return repository.save(updateLocality);
    }
}
