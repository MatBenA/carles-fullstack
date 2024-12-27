package com.grupocarles.admin.landsurveys.admin_landsurveys.specification;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.LandSurvey;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NotificationSpecification implements Specification<LandSurvey> {

    private String setting;
    private Double value;

    @Override
    public Predicate toPredicate(Root<LandSurvey> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if("statisticTopLimit".equals(setting)){
            Double settingValue = Double.parseDouble(setting);
            Path<LocalDateTime> priceVerificationDate = root.get("priceVerificationDate");

            // Calculate the age in seconds using EXTRACT(EPOCH FROM AGE(...))
            Expression<Double> ageInSeconds = criteriaBuilder.function(
                    "EXTRACT",
                    Double.class,
                    criteriaBuilder.literal("EPOCH"), // Extract epoch (seconds)
                    criteriaBuilder.function("AGE", String.class, criteriaBuilder.currentTimestamp(), priceVerificationDate)
            );

            // Calculate the threshold (value * 86400)
            Expression<Double> threshold = criteriaBuilder.prod(criteriaBuilder.literal(value), 86400.0);

            // Create the predicate
            Predicate predicate = criteriaBuilder.greaterThan(ageInSeconds, threshold);

            query.orderBy(criteriaBuilder.desc(priceVerificationDate));
            predicateList.add(predicate);
        }

        if("statisticBottomLimit".equals(setting)){
            Predicate predicate = criteriaBuilder.greaterThan(
                    criteriaBuilder.function(
                            "DATE_PART", double.class,
                            criteriaBuilder.literal("day"),
                            criteriaBuilder.currentTimestamp(),
                            root.get("priceVerificationDate")
                    ),
                    value
            );
            predicateList.add(predicate);
        }

        if("particularValidity".equals(setting)){
            Predicate predicate = criteriaBuilder.greaterThan(
                    criteriaBuilder.function(
                            "DATE_PART", double.class,
                            criteriaBuilder.literal("day"),
                            criteriaBuilder.currentTimestamp(),
                            root.get("priceVerificationDate")
                    ),
                    value
            );
            predicateList.add(predicate);
        }

        if("agencyValidity".equals(setting)){
            Predicate predicate = criteriaBuilder.greaterThan(
                    criteriaBuilder.function(
                            "DATE_PART", double.class,
                            criteriaBuilder.literal("day"),
                            criteriaBuilder.currentTimestamp(),
                            root.get("priceVerificationDate")
                    ),
                    value
            );
            predicateList.add(predicate);
        }

        if("unworkableValidity".equals(setting)){
            Predicate predicate = criteriaBuilder.greaterThan(
                    criteriaBuilder.function(
                            "DATE_PART", double.class,
                            criteriaBuilder.literal("day"),
                            criteriaBuilder.currentTimestamp(),
                            root.get("priceVerificationDate")
                    ),
                    value
            );
            predicateList.add(predicate);
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
