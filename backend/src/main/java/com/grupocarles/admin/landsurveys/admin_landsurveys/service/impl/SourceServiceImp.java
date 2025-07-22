package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.SourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Source;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SourceRepository;

@RequiredArgsConstructor
@Service
public class SourceServiceImp implements SourceService {

    private final SourceRepository repository;

    @Override
    public Source createSource(Source source) {
        return repository.save(source);
    }

    @Override
    public void deleteSource(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public Source getSourceById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Source> getAllSources() {
        return repository.findAll();
    }

    @Override
    public Source updateSource(long id, Source newSource) {
        Source updateSource = repository.findById(id).orElse(null);

        updateSource.setName(newSource.getName());

        return repository.save(updateSource);
    }
}

