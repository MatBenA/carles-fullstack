package com.grupocarles.admin.landsurveys.admin_landsurveys.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record LandSurveyDTO(long id,
                            LocalDateTime date,
                            String address,
                            Boolean corner,
                            Boolean title,
                            String titleSituation,
                            String measurements,
                            BigDecimal surface,
                            LocalDateTime priceVerificationDate,
                            LocalDateTime reassessmentDate,
                            String observation,
                            Boolean isRescinded,
                            Boolean isArchived,
                            SelectOptionDTO surveyor,
                            SelectOptionDTO manager,
                            String fileType,
                            String roadType,
                            String locality,
                            String section,
                            String zone,
                            String source,
                            String classification,
                            String agency,
                            String particular,
                            String contact,
                            Long price,
                            String currency,
                            String folder,
                            List<AssessmentDTO> assessmentList) {
}
