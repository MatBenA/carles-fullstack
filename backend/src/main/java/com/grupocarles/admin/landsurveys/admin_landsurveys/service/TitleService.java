package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Title;

import java.util.List;

public interface TitleService {
    Title createTitleBySituation(String situation);

    List<Title> getAllTitles();

    Title getTitleById(long id);

    Title updateTitle(long id, String newSituation);

    void deleteTitle(long id);

    List<SelectOptionDTO> getAllAgenciesAsOptions();

    Boolean deleteTitleByName(String situation);
}
