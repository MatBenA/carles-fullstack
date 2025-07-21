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
        Long maxCode = folderRepository.findMaxCode();
        long newCode = (maxCode != null ? maxCode : 0) + 1;

        Folder newFolder = new Folder();
        newFolder.setCode(newCode);
        return folderRepository.save(newFolder);
    }

    @Override
    public void deleteFolder(Long code) {
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

    public SelectOptionDTO toDto(Folder folder){
        return new SelectOptionDTO(
                folder.getCode().toString(),
                folder.getCode().toString());}
}
