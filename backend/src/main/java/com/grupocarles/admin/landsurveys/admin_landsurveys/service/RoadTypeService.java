package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.RoadType;

public interface RoadTypeService {
    RoadType createRoadType(RoadType roadType);

    List<RoadType> getAllRoadTypes();

    RoadType getRoadTypeById(long id);

    RoadType updateRoadType(long id, RoadType newRoadType);

    void deleteRoadType(long id);
}

