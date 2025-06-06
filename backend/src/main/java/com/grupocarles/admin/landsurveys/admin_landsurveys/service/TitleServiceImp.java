package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Title;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.TitleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleServiceImp implements TitleService{
    @Autowired
    public TitleRepository repository;

    @Override
    public Title createTitleBySituation(String situation) {
        Title newTitle = new Title();
        newTitle.setSituation(situation);
        return repository.save(newTitle);
    }

    @Override
    public List<Title> getAllTitles() {
        return repository.findAll();
    }

    @Override
    public Title getTitleById(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Title updateTitle(long id, String newSituation) {
        Title title = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        title.setSituation(newSituation);
        return repository.save(title);
    }

    @Override
    public void deleteTitle(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<SelectOptionDTO> getAllAgenciesAsOptions() {
        return repository.findAll()
                .stream()
                .map(title -> new SelectOptionDTO(title.getId().toString(), title.getSituation()))
                .toList();
    }

    @Override
    public Boolean deleteTitleByName(String situation) {
        Title title = repository
                .findBySituation(situation)
                .orElseThrow(EntityNotFoundException::new);
        repository.delete(title);
        return true;
    }
}
