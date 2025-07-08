package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Folder;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.FolderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImp implements FolderService{

    @Autowired
    private FolderRepository folderRepository;

    @Override
    public List<SelectOptionDTO> getFolderOptions() {
        return folderRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Folder createFolder() {
        // Find the folder with the highest code
        String maxCode = folderRepository.findMaxCode();
        String newCode;

        if (maxCode != null && maxCode.startsWith("V")) {
            // Extract the numeric part and increment it
            int numericPart = Integer.parseInt(maxCode.substring(1));
            newCode = "V" + (numericPart + 1);
        } else {
            // Default starting code if no folders exist
            newCode = "V1";
        }

        // Create and save the new folder
        Folder newFolder = new Folder();
        newFolder.setCode(newCode);
        return folderRepository.save(newFolder);
    }

    @Override
    public void deleteFolder(String code) {
        Long folderId = folderRepository.findByCode(code).orElseThrow(EntityNotFoundException::new).getId();
        folderRepository.deleteById(folderId);
    }

    @Override
    public List<SelectOptionDTO> getFreeFolderOptions() {
        return folderRepository
                .findFolderWithoutLandSurvey()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public SelectOptionDTO toDto(Folder folder){return new SelectOptionDTO(folder.getCode(), folder.getCode());}
}
