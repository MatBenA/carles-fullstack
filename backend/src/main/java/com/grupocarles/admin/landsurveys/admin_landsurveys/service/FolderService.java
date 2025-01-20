package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Folder;

import java.util.List;

public interface FolderService {
    List<SelectOptionDTO> getFreeFolderOptions();
    Folder createFolder();
    void deleteFolder(String code);
}
