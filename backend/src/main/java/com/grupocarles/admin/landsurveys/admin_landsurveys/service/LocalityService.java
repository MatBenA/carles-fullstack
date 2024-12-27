package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Locality;

public interface LocalityService {
    Locality createLocality(Locality locality);

    List<Locality> getAllLocalities();

    List<SelectOptionDTO> getAllLocalitiesAsOptions();

    Locality getLocalityById(long id);

    Locality updateLocality(long id, Locality newLocality);

    void deleteLocality(long id);
}