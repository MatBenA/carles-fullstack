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
    private Boolean rescinded;
    private String folder;
    private String title;

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

        if (StringUtils.hasText(businessEvaluation) && "cheapFilter".equals(businessEvaluation)) {
            Join<LandSurvey, Assessment> assessmentJoin = root.join("assessmentList");
            query.groupBy(root.get("id"));
            Predicate avgPricePredicate = criteriaBuilder.gt(
                    criteriaBuilder.avg(assessmentJoin.get("price")),
                    10.0
            );
            query.having(avgPricePredicate);
        }

        if (StringUtils.hasText(section)) {
            Join<LandSurvey, Section> landSurveySectionJoin = root.join("section");
            Expression<String> sectionNameToUpperCase = criteriaBuilder.upper(landSurveySectionJoin.get("name"));
            Predicate sectionNameLikePredicate = criteriaBuilder.equal(sectionNameToUpperCase, section.toUpperCase());
            predicateList.add(sectionNameLikePredicate);
        }

        if (StringUtils.hasText(zone)) {
            Join<LandSurvey, Zone> landSurveyZoneJoin = root.join("zone");
            Expression<String> zoneNameToUpperCase = criteriaBuilder.upper(landSurveyZoneJoin.get("name"));
            Predicate zoneNameLikePredicate = criteriaBuilder.equal(zoneNameToUpperCase, zone.toUpperCase());
            predicateList.add(zoneNameLikePredicate);
        }

        if (StringUtils.hasText(agency)) {
            Join<LandSurvey, Agency> landSurveyAgencyJoin = root.join("agency");
            Expression<String> agencyNameToUpperCase = criteriaBuilder.upper(landSurveyAgencyJoin.get("name"));
            Predicate agencyNameLikePredicate = criteriaBuilder.like(agencyNameToUpperCase, "%" + agency.toUpperCase() + "%");
            predicateList.add(agencyNameLikePredicate);
        }

        if (StringUtils.hasText(particular)) {
            //This Left Join is necessary cause otherwise LandSurveys with null Particular would not be retrieved
            Join<LandSurvey, Particular> landSurveyParticularJoin = root.join("particular", JoinType.LEFT);
            Expression<String> particularNameToUpperCase = criteriaBuilder.upper(landSurveyParticularJoin.get("name"));
            Predicate particularNameLikePredicate = criteriaBuilder.like(particularNameToUpperCase, "%" + particular.toUpperCase() + "%");
            predicateList.add(particularNameLikePredicate);
        }

        if (StringUtils.hasText(classification)){
            Join<LandSurvey, Classification> landSurveyClassificationJoin = root.join("classification");
            Expression<String> classificationNameToUpperCase = criteriaBuilder.upper(landSurveyClassificationJoin.get("name"));
            Predicate classifiactionNameLikePredicate = criteriaBuilder.like(classificationNameToUpperCase, "%" + classification.toUpperCase() + "%");
            predicateList.add(classifiactionNameLikePredicate);
        }
        if (title != null){ //todo fix
            Join<LandSurvey, Title> landSurveyTitleJoin = root.join("title");
            Predicate titleSituationLikePredicate = criteriaBuilder.equal(landSurveyTitleJoin.get("situation"), title);
            predicateList.add(titleSituationLikePredicate);
        }

        if (managerEmail != null){
            Join<LandSurvey, UserSec> landSurveyManagerJoin = root.join("manager");
            Predicate managerEmailLikePredicate = criteriaBuilder.like(landSurveyManagerJoin.get("email"), managerEmail);
            predicateList.add(managerEmailLikePredicate);
        }

        if (rescinded != null) {
            Predicate isRescindedPredicate = criteriaBuilder.equal(root.get("isRescinded"), rescinded);
            predicateList.add(isRescindedPredicate);
        }

        if(managerEmail != null) {
            query.orderBy(criteriaBuilder.asc(root.get("priceVerificationDate")));
        }else {
            query.orderBy(criteriaBuilder.asc(root.get("price")));
        }

        if(folder != null){
            predicateList.clear();
            Join<LandSurvey, Folder> landSurveyFolderJoin = root.join("folder");
            Predicate folderPredicate = criteriaBuilder.equal(landSurveyFolderJoin.get("code"), folder);
            predicateList.add(folderPredicate);
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}