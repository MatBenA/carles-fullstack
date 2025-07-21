package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Folder;

import java.util.List;

public interface FolderService {
    List<SelectOptionDTO> getFolderOptions();
    Folder createFolder();
    void deleteFolder(Long code);
    List<SelectOptionDTO> getFreeFolderOptions();
}
