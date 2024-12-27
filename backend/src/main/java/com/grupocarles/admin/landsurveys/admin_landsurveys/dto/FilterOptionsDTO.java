package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Agency;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Particular;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Section;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.Zone;

import java.util.List;

public record FilterOptionsDTO(List<Section> sectionList,
                               List<Zone> zoneList,
                               List<Agency> agencyList,
                               List<Particular> particularList) {
}