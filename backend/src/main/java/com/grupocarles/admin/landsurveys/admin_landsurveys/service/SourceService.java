package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Source;

public interface SourceService {
    Source createSource(Source source);

    List<Source> getAllSources();

    Source getSourceById(long id);

    Source updateSource(long id, Source newSource);

    void deleteSource(long id);
}

