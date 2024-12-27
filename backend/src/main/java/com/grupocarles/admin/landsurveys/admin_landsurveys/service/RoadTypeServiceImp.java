package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.RoadType;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.RoadTypeRepository;

@Service
public class RoadTypeServiceImp implements RoadTypeService {

    @Autowired
    private RoadTypeRepository repository;

    @Override
    public RoadType createRoadType(RoadType roadType) {
        return repository.save(roadType);
    }

    @Override
    public void deleteRoadType(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public RoadType getRoadTypeById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<RoadType> getAllRoadTypes() {
        return repository.findAll();
    }

    @Override
    public RoadType updateRoadType(long id, RoadType newRoadType) {
        RoadType updateRoadType = repository.findById(id).orElse(null);

        updateRoadType.setName(newRoadType.getName());

        return repository.save(updateRoadType);
    }
}

