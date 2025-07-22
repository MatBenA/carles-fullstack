package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.SettingDTO;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Setting;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.SettingRepository;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.SettingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SettingServiceImp implements SettingService {

    private final SettingRepository settingRepository;

    @Override
    @Transactional
    public List<SettingDTO> getSettings() {
        return settingRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public SettingDTO updateSetting(SettingDTO settingDTO) {

        if (settingDTO.name().isEmpty() || settingDTO.value().isEmpty()){
            throw new InvalidParameterException();
        }

        Setting setting = settingRepository
                .findById(settingDTO.name())
                .orElseThrow();
        setting.setValue(settingDTO.value());
        return toDTO(settingRepository.save(setting));
    }

    @Override
    public Long getSettingByName(String settingName) {
        return Long.parseLong(settingRepository
                .findById(settingName)
                .orElseThrow(() -> new EntityNotFoundException("Setting " + settingName + " not found"))
                .getValue());
    }

    public SettingDTO toDTO(Setting setting){
        return new SettingDTO(setting.getName(), setting.getValue());
    }
}
