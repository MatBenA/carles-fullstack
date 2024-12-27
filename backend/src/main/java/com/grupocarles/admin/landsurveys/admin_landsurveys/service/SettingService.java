package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SettingDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SettingService {

    List<SettingDTO> getSettings();

    SettingDTO updateSetting(SettingDTO settingDTO);

    Long getSettingByName(String settingName);
}
