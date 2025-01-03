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
    private Boolean title;

    private Long cheapFlag;
    private Long expensiveFlag;

    @Override
    public Predicate toPredicate(Root<LandSurvey> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (minPrice != null && minPrice > 0){
            Predicate priceGreaterThanEqualPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            predicateList.add(priceGreaterThanEqualPredicate);
        }

        if (maxPrice != null && maxPrice > 0) {
            Predicate priceLessThanEqualPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            predicateList.add(priceLessThanEqualPredicate);
        }

        if (StringUtils.hasText(address)) {
            Expression<String> addressToUpperCase = criteriaBuilder.upper(root.get("address"));
            Predicate addressLikePredicate = criteriaBuilder.like(addressToUpperCase, "%" + address.toUpperCase() + "%");
            predicateList.add(addressLikePredicate);
        }

        /*if (StringUtils.hasText(businessEvaluation) && "cheapFilter".equals(businessEvaluation)) {
            Join<LandSurvey, Assessment> landSurveyAssessmentJoin = root.join("assessmentList");
            Subquery<Double> averageAssessmentSubquery = criteriaBuilder.createQuery().subquery(Double.class);
            Root<Assessment> subqueryRoot = averageAssessmentSubquery.from(Assessment.class);
            averageAssessmentSubquery.select(criteriaBuilder.avg(subqueryRoot.get("price")))
                    .where(criteriaBuilder.equal(subqueryRoot.get("landSurvey").get("id"), root.get("id")));

            Expression<Double> assessmentAverage = criteriaBuilder.avg(landSurveyAssessmentJoin.get("price"));
            Expression<Number> absoluteEvaluation = criteriaBuilder.quot(root.get("price"), assessmentAverage);
            Expression<Number> relativeEvaluation = criteriaBuilder.diff(absoluteEvaluation, 1);
            Expression<Number> businessEvaluation = criteriaBuilder.prod(relativeEvaluation, 100);
            Expression<Long> cheapFlagExpression = criteriaBuilder.literal(cheapFlag);
            Predicate businessEvaluationGreaterThanPredicate = criteriaBuilder.lessThanOrEqualTo(businessEvaluation.as(Long.class), cheapFlagExpression);
            predicateList.add(businessEvaluationGreaterThanPredicate);
        }*/

        if (StringUtils.hasText(section)) {
            Join<LandSurvey, Section> landSurveySectionJoin = root.join("section");
            Expression<String> sectionNameToUpperCase = criteriaBuilder.upper(landSurveySectionJoin.get("name"));
            Predicate sectionNameLikePredicate = criteriaBuilder.equal(sectionNameToUpperCase, section.toUpperCase());
            predicateList.add(sectionNameLikePredicate);
        }

        Join<LandSurvey, Zone> landSurveyZoneJoin = root.join("zone");
        if (StringUtils.hasText(zone)) {
            Expression<String> zoneNameToUpperCase = criteriaBuilder.upper(landSurveyZoneJoin.get("name"));
            Predicate zoneNameLikePredicate = criteriaBuilder.equal(zoneNameToUpperCase, zone.toUpperCase());
            predicateList.add(zoneNameLikePredicate);
        }

        Join<LandSurvey, Agency> landSurveyAgencyJoin = root.join("agency");
        if (StringUtils.hasText(agency)) {
            Expression<String> agencyNameToUpperCase = criteriaBuilder.upper(landSurveyAgencyJoin.get("name"));
            Predicate agencyNameLikePredicate = criteriaBuilder.like(agencyNameToUpperCase, "%" + agency.toUpperCase() + "%");
            predicateList.add(agencyNameLikePredicate);
        }

        Join<LandSurvey, Particular> landSurveyParticularJoin = root.join("particular");
        if (StringUtils.hasText(particular)) {
            Expression<String> particularNameToUpperCase = criteriaBuilder.upper(landSurveyParticularJoin.get("name"));
            Predicate particularNameLikePredicate = criteriaBuilder.like(particularNameToUpperCase, "%" + particular.toUpperCase() + "%");
            predicateList.add(particularNameLikePredicate);
        }

        Join<LandSurvey, Classification> landSurveyClassificationJoin = root.join("classification");
        if (StringUtils.hasText(classification)){
            Expression<String> classificationNameToUpperCase = criteriaBuilder.upper(landSurveyClassificationJoin.get("name"));
            Predicate classifiactionNameLikePredicate = criteriaBuilder.like(classificationNameToUpperCase, "%" + classification.toUpperCase() + "%");
            predicateList.add(classifiactionNameLikePredicate);
        }

        if (title != null){
            Predicate hasTitlePredicate = criteriaBuilder.equal(root.get("title"), title);
            predicateList.add(hasTitlePredicate);
        }

        Join<LandSurvey, UserSec> landSurveyManagerJoin = root.join("manager");
        if (managerEmail != null){
            Predicate managerEmailLikePredicate = criteriaBuilder.like(landSurveyManagerJoin.get("email"), managerEmail);
            predicateList.add(managerEmailLikePredicate);
        }

        if(managerEmail != null) {
            query.orderBy(criteriaBuilder.asc(root.get("priceVerificationDate")));
        }else {
            query.orderBy(criteriaBuilder.asc(root.get("price")));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
