package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Section;

public interface SectionService {
    Section createSection(Section section);

    List<Section> getAllSections();

    Section getSectionById(long id);

    Section updateSection(long id, Section newSection);

    void deleteSection(long id);

    List<SelectOptionDTO> getAllSectionsAsOptions();
}