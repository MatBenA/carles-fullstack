package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Zone;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.ZoneRepository;

@Service
public class ZoneServiceImp implements ZoneService {

    @Autowired
    private ZoneRepository repository;

    @Override
    public Zone createZone(Zone zone) {
        return repository.save(zone);
    }

    @Override
    public void deleteZone(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public List<SelectOptionDTO> getAllZonesAsOptions() {
        return repository.findAll().stream()
                .map(zone -> new SelectOptionDTO(zone.getId().toString(), zone.getName()))
                .toList();
    }

    @Override
    public Zone getZoneById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Zone> getAllZones() {
        return repository.findAll();
    }

    @Override
    public Zone updateZone(long id, Zone newZone) {
        Zone updateZone = repository.findById(id).orElse(null);

        updateZone.setName(newZone.getName());

        return repository.save(updateZone);
    }
}
