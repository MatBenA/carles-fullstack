package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.RoadTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.RoadType;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.RoadTypeRepository;

@RequiredArgsConstructor
@Service
public class RoadTypeServiceImp implements RoadTypeService {

    private final RoadTypeRepository repository;

    @Override
    public RoadType createRoadType(RoadType roadType) {
        return repository.save(roadType);
    }

    @Override
    public void deleteRoadType(long id) {
        repository.deleteById(id);
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

