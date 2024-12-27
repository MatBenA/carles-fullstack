package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.FileType;

public interface FileTypeService {
    FileType createFileType(FileType fileType);

    List<FileType> getAllFileTypes();

    FileType getFileTypeById(long id);

    FileType updateFileType(long id, FileType newFileType);

    void deleteFileType(long id);
}

