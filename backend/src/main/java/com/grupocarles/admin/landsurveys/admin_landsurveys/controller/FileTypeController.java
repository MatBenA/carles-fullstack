package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.FileType;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.FileTypeService;


@RestController
@RequestMapping("/file-types")
public class FileTypeController {

    @Autowired
    private FileTypeService service;

    @PostMapping("/create")
    public FileType createFileType(@RequestBody FileType fileType) {
        return service.createFileType(fileType);
    }

    @GetMapping
    public List<FileType> getAllAgencies() {
        return service.getAllFileTypes();
    }

    @GetMapping("/{id}")
    public FileType getFileTypeById(@PathVariable long id) {
        return service.getFileTypeById(id);
    }

    @PutMapping("/update/{id}")
    public FileType updateFileType(@PathVariable long id, @RequestBody FileType newFileType){
        return service.updateFileType(id, newFileType);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteFileType(@PathVariable long id){
        service.deleteFileType(id);
        return "FileType deleted successfully";
    }
}


