package com.grupocarles.admin.landsurveys.admin_landsurveys.specification;

import com.grupocarles.admin.landsurveys.admin_landsurveys.model.*;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SearchLandSurveySpecification implements Specification<LandSurvey> {

    private Integer minPrice;
    private Integer maxPrice;
    private String address;
    private String businessEvaluation;
    private String section;
    private String zone;
    private String agency;
    private String particular;
    private String classification;
    private String managerEmail;
    private String surveyorEmail;
    private Boolean rescinded;
    private String folder;
    private String title;
    private Long cheapFlag;
    private Long expensiveFlag;

    @Override
    public Predicate toPredicate(Root<LandSurvey> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        // Add fetch joins to prevent N+1 queries (only for select queries, not count queries)
        if (Long.class != query.getResultType()) {
            addFetchJoins(root);
        }

        // Price filters
        addPriceFilters(predicateList, root, criteriaBuilder);

        // Address filter
        addAddressFilter(predicateList, root, criteriaBuilder);

        // Business evaluation filter
        addBusinessEvaluationFilter(predicateList, root, query, criteriaBuilder);

        // Entity filters with joins
        addEntityFilters(predicateList, root, criteriaBuilder);

        // Boolean filters
        addBooleanFilters(predicateList, root, criteriaBuilder);

        // Folder filter (special handling)
        addFolderFilter(predicateList, root, criteriaBuilder);

        // Ordering
        addOrdering(root, query, criteriaBuilder);

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

    private void addFetchJoins(Root<LandSurvey> root) {
        // Add fetch joins for commonly accessed relationships
        root.fetch("assessmentList", JoinType.LEFT);
        root.fetch("agency", JoinType.LEFT);
        root.fetch("particular", JoinType.LEFT);
        root.fetch("contact", JoinType.LEFT);
        root.fetch("zone", JoinType.LEFT);
        root.fetch("manager", JoinType.LEFT);
        root.fetch("surveyor", JoinType.LEFT);
        root.fetch("source", JoinType.LEFT);
        root.fetch("section", JoinType.LEFT);
        root.fetch("folder", JoinType.LEFT);
        root.fetch("title", JoinType.LEFT);
        root.fetch("surveyor", JoinType.LEFT);
        root.fetch("fileType", JoinType.LEFT);
        root.fetch("roadType", JoinType.LEFT);
        root.fetch("locality", JoinType.LEFT);
        root.fetch("classification", JoinType.LEFT);
        root.fetch("currency", JoinType.LEFT);
    }

    private void addPriceFilters(List<Predicate> predicateList, Root<LandSurvey> root, CriteriaBuilder criteriaBuilder) {
        if (minPrice != null && minPrice > 0) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null && maxPrice > 0) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
    }

    private void addAddressFilter(List<Predicate> predicateList, Root<LandSurvey> root, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.hasText(address)) {
            Expression<String> addressToUpperCase = criteriaBuilder.upper(root.get("address"));
            Predicate addressLikePredicate = criteriaBuilder.like(addressToUpperCase, "%" + address.toUpperCase() + "%");
            predicateList.add(addressLikePredicate);
        }
    }

    private void addBusinessEvaluationFilter(List<Predicate> predicateList, Root<LandSurvey> root,
                                             CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.hasText(businessEvaluation)) {
            switch (businessEvaluation.toLowerCase()) {
                case "cheapfilter":
                    addCheapFilter(predicateList, root, query, criteriaBuilder);
                    break;
                case "expensivefilter":
                    addExpensiveFilter(predicateList, root, query, criteriaBuilder);
                    break;
                // Add more cases as needed
            }
        }
    }

    private void addCheapFilter(List<Predicate> predicateList, Root<LandSurvey> root,
                                CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<LandSurvey, Assessment> assessmentJoin = root.join("assessmentList", JoinType.LEFT);
        query.groupBy(root.get("id"));

        // Use cheapFlag if available, otherwise use hardcoded value
        double threshold = cheapFlag != null ? cheapFlag.doubleValue() : 10.0;

        Predicate avgPricePredicate = criteriaBuilder.gt(
                criteriaBuilder.avg(assessmentJoin.get("price")),
                threshold
        );
        query.having(avgPricePredicate);
    }

    private void addExpensiveFilter(List<Predicate> predicateList, Root<LandSurvey> root,
                                    CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Join<LandSurvey, Assessment> assessmentJoin = root.join("assessmentList", JoinType.LEFT);
        query.groupBy(root.get("id"));

        // Use expensiveFlag if available, otherwise use hardcoded value
        double threshold = expensiveFlag != null ? expensiveFlag.doubleValue() : 100.0;

        Predicate avgPricePredicate = criteriaBuilder.lt(
                criteriaBuilder.avg(assessmentJoin.get("price")),
                threshold
        );
        query.having(avgPricePredicate);
    }

    private void addEntityFilters(List<Predicate> predicateList, Root<LandSurvey> root, CriteriaBuilder criteriaBuilder) {
        // Section filter
        if (StringUtils.hasText(section)) {
            Join<LandSurvey, Section> sectionJoin = root.join("section", JoinType.LEFT);
            Expression<String> sectionNameUpper = criteriaBuilder.upper(sectionJoin.get("name"));
            predicateList.add(criteriaBuilder.equal(sectionNameUpper, section.toUpperCase()));
        }

        // Zone filter
        if (StringUtils.hasText(zone)) {
            Join<LandSurvey, Zone> zoneJoin = root.join("zone", JoinType.LEFT);
            Expression<String> zoneNameUpper = criteriaBuilder.upper(zoneJoin.get("name"));
            predicateList.add(criteriaBuilder.equal(zoneNameUpper, zone.toUpperCase()));
        }

        // Agency filter
        if (StringUtils.hasText(agency)) {
            Join<LandSurvey, Agency> agencyJoin = root.join("agency", JoinType.LEFT);
            Expression<String> agencyNameUpper = criteriaBuilder.upper(agencyJoin.get("name"));
            predicateList.add(criteriaBuilder.like(agencyNameUpper, "%" + agency.toUpperCase() + "%"));
        }

        // Particular filter
        if (StringUtils.hasText(particular)) {
            Join<LandSurvey, Particular> particularJoin = root.join("particular", JoinType.LEFT);
            Expression<String> particularNameUpper = criteriaBuilder.upper(particularJoin.get("name"));
            predicateList.add(criteriaBuilder.like(particularNameUpper, "%" + particular.toUpperCase() + "%"));
        }

        // Classification filter
        if (StringUtils.hasText(classification)) {
            Join<LandSurvey, Classification> classificationJoin = root.join("classification", JoinType.LEFT);
            Expression<String> classificationNameUpper = criteriaBuilder.upper(classificationJoin.get("name"));
            predicateList.add(criteriaBuilder.like(classificationNameUpper, "%" + classification.toUpperCase() + "%"));
        }

        // Title filter
        if (StringUtils.hasText(title)) {
            Join<LandSurvey, Title> titleJoin = root.join("title", JoinType.LEFT);
            predicateList.add(criteriaBuilder.equal(titleJoin.get("situation"), title));
        }

        // Manager email filter
        if (StringUtils.hasText(managerEmail)) {
            Join<LandSurvey, UserSec> managerJoin = root.join("manager", JoinType.LEFT);
            predicateList.add(criteriaBuilder.like(managerJoin.get("email"), "%" + managerEmail + "%"));
        }

        // Surveyor email filter
        if (StringUtils.hasText(surveyorEmail)) {
            Join<LandSurvey, UserSec> surveyorJoin = root.join("surveyor", JoinType.LEFT);
            predicateList.add(criteriaBuilder.like(surveyorJoin.get("email"), "%" + surveyorEmail + "%"));
        }
    }

    private void addBooleanFilters(List<Predicate> predicateList, Root<LandSurvey> root, CriteriaBuilder criteriaBuilder) {
        if (rescinded != null) {
            predicateList.add(criteriaBuilder.equal(root.get("isRescinded"), rescinded));
        }
    }

    private void addFolderFilter(List<Predicate> predicateList, Root<LandSurvey> root, CriteriaBuilder criteriaBuilder) {
        // Always join folder for ordering
        Join<LandSurvey, Folder> folderJoin = root.join("folder", JoinType.LEFT);

        // FIXED: Don't clear all predicates when folder filter is applied
        if (StringUtils.hasText(folder)) {
            predicateList.add(criteriaBuilder.equal(folderJoin.get("code"), folder));
        }
    }

    private void addOrdering(Root<LandSurvey> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        // Only add ordering for select queries, not count queries
        if (Long.class != query.getResultType()) {
            Join<LandSurvey, Folder> folderJoin = root.join("folder", JoinType.LEFT);
            query.orderBy(criteriaBuilder.asc(folderJoin.get("id")));
        }
    }
}