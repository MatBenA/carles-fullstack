package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Section;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SectionRepository;

@Service
public class SectionServiceImp implements SectionService {

    @Autowired
    private SectionRepository repository;

    @Override
    public Section createSection(Section section) {
        return repository.save(section);
    }

    @Override
    public void deleteSection(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public List<SelectOptionDTO> getAllSectionsAsOptions() {
        return repository.findAll().stream().map(section -> new SelectOptionDTO(section.getId().toString(), section.getName())).toList();
    }

    @Override
    public Section getSectionById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Section> getAllSections() {
        return repository.findAll();
    }

    @Override
    public Section updateSection(long id, Section newSection) {
        Section updateSection = repository.findById(id).orElse(null);

        updateSection.setName(newSection.getName());

        return repository.save(updateSection);
    }
}
