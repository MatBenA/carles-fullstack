package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Agency;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.AgencyRepository;

@Service
public class AgencyServiceImp implements AgencyService {

    @Autowired
    private AgencyRepository repository;

    @Override
    public Agency createAgency(Agency agency) {
        return repository.save(agency);
    }

    @Override
    public void deleteAgency(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<SelectOptionDTO> getAllAgenciesAsOptions() {
        return repository.findAll()
                .stream()
                .map(agency -> new SelectOptionDTO(agency.getId().toString(), agency.getName()))
                .toList();
    }

    @Override
    public Agency getAgencyById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Agency> getAllAgencies() {
        return repository.findAll();
    }

    @Override
    public Agency updateAgency(long id, Agency newAgency) {
        Agency updateAgency = repository.findById(id).orElse(null);

        updateAgency.setName(newAgency.getName());

        return repository.save(updateAgency);
    }
}
