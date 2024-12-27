package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Particular;

public interface ParticularService {
    Particular createParticular(Particular particular);

    List<Particular> getAllParticulars();

    Particular getParticularById(long id);

    Particular updateParticular(long id, Particular newParticular);

    void deleteParticular(long id);

    List<SelectOptionDTO> getAllParticularsAsOptions();
}

