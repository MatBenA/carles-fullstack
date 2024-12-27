package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Particular;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.ParticularRepository;

@Service
public class ParticularServiceImp implements ParticularService {

    @Autowired
    private ParticularRepository repository;

    @Override
    public Particular createParticular(Particular particular) {
        return repository.save(particular);
    }

    @Override
    public void deleteParticular(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public List<SelectOptionDTO> getAllParticularsAsOptions() {
        return repository.findAll()
                .stream()
                .map(particular -> new SelectOptionDTO(particular.getId().toString(), particular.getName()))
                .toList();
    }

    @Override
    public Particular getParticularById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Particular> getAllParticulars() {
        return repository.findAll();
    }

    @Override
    public Particular updateParticular(long id, Particular newParticular) {
        Particular updateParticular = repository.findById(id).orElse(null);

        updateParticular.setName(newParticular.getName());

        return repository.save(updateParticular);
    }
}

