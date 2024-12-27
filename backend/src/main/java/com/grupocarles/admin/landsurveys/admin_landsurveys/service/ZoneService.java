package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Zone;

public interface ZoneService {
    Zone createZone(Zone zone);

    List<Zone> getAllZones();

    Zone getZoneById(long id);

    Zone updateZone(long id, Zone newZone);

    void deleteZone(long id);

    List<SelectOptionDTO> getAllZonesAsOptions();
}