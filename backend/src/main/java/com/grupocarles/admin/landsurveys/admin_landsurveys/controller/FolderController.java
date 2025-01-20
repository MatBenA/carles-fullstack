package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SelectOptionDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Folder;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.FolderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @GetMapping("/options")
    public List<SelectOptionDTO> getFolderOptions(){
        return folderService.getFreeFolderOptions();
    };

    @GetMapping("/create")
    public Folder createFolder(){
        return folderService.createFolder();
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<String> deleteFolder(@PathVariable String code){
        try {
            folderService.deleteFolder(code);
            return ResponseEntity.ok("Folder deleted successfully.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The folder with code " + code + " does not exist.");
        }
    }
}
