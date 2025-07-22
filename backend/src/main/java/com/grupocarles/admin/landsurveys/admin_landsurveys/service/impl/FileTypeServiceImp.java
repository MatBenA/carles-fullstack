package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.service.FileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.FileType;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.FileTypeRepository;

@Service
public class FileTypeServiceImp implements FileTypeService {

    @Autowired
    private FileTypeRepository repository;

    @Override
    public FileType createFileType(FileType fileType) {
        return repository.save(fileType);
    }

    @Override
    public void deleteFileType(long id) {
        repository.deleteById(id);
        ;
    }

    @Override
    public FileType getFileTypeById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<FileType> getAllFileTypes() {
        return repository.findAll();
    }

    @Override
    public FileType updateFileType(long id, FileType newFileType) {
        FileType updateFileType = repository.findById(id).orElse(null);

        updateFileType.setName(newFileType.getName());

        return repository.save(updateFileType);
    }
}

