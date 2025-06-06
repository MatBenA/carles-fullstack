package com.grupocarles.admin.landsurveys.admin_landsurveys.controller;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SettingDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private SettingService settingService;

    @GetMapping
    public List<SettingDTO> getSettings() {
        return settingService.getSettings();
    }

    @PutMapping
    public SettingDTO updateSetting(@RequestBody SettingDTO settingDTO) {
        return settingService.updateSetting(settingDTO);
    }

    @GetMapping("/{settingName}")
    public Long getSettingByName(@PathVariable String settingName) {
        return settingService.getSettingByName(settingName);
    }
}
