package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Source;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SourceRepository;

@Service
public class SourceServiceImp implements SourceService {

    @Autowired
    private SourceRepository repository;

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

