package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Agency;

public interface AgencyService {
    Agency createAgency(Agency agency);

    List<Agency> getAllAgencies();

    Agency getAgencyById(long id);

    Agency updateAgency(long id, Agency newAgency);

    void deleteAgency(long id);

    List<SelectOptionDTO> getAllAgenciesAsOptions();
}