package com.grupocarles.admin.landsurveys.admin_landsurveys.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.AssessmentService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.LandSurveyService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.SettingService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.service.UserService;
import com.grupocarles.admin.landsurveys.admin_landsurveys.specification.SearchLandSurveySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LandSurveyServiceImp implements LandSurveyService {
    private final LandSurveyRepository landSurveyRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AssessmentService assessmentService;
    private final FileTypeRepository fileTypeRepository;
    private final RoadTypeRepository roadTypeRepository;
    private final LocalityRepository localityRepository;
    private final SectionRepository sectionRepository;
    private final ZoneRepository zoneRepository;
    private final SourceRepository sourceRepository;
    private final ClassificationRepository classificationRepository;
    private final AgencyRepository agencyRepository;
    private final ParticularRepository particularRepository;
    private final ContactRepository contactRepository;
    private final CurrencyRepository currencyRepository;
    private final SettingService settingService;
    private final SettingRepository settingRepository;
    private final FolderRepository folderRepository;
    private final TitleRepository titleRepository;

    @Override
    @Transactional
    public LandSurveyDTO createLandSurvey(LandSurveyDTO landSurveyDTO) {

        LandSurvey landSurvey = DTOToLandSurvey(landSurveyDTO);
        landSurvey.setIsRescinded(false);
        landSurvey.setIsArchived(false);
        landSurvey.setPriceVerificationDate(LocalDateTime.now());
        landSurvey.setReassessmentDate(LocalDateTime.now());

        return adaptToDTO(saveSecure(landSurvey));
    }

    private LandSurvey DTOToLandSurvey(LandSurveyDTO landSurveyDTO) {

        LandSurvey landSurvey = new LandSurvey();
        UserSec surveyor = userRepository.findUserEntityByEmail(landSurveyDTO.surveyor().value())
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + landSurveyDTO.surveyor().value()));
        UserSec manager = userRepository.findUserEntityByEmail(landSurveyDTO.manager().value())
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + landSurveyDTO.manager().value()));
        FileType fileType = fileTypeRepository.findByName(landSurveyDTO.fileType())
                .orElseThrow(() -> new EntityNotFoundException("FileType not found for name: " + landSurveyDTO.fileType()));
        RoadType roadType = roadTypeRepository.findByName(landSurveyDTO.roadType())
                .orElseThrow(() -> new EntityNotFoundException("RoadType not found for name: " + landSurveyDTO.roadType()));
        Source source = sourceRepository.findByName(landSurveyDTO.source())
                .orElseThrow(() -> new EntityNotFoundException("Source not found for name: " + landSurveyDTO.source()));
        Classification classification = classificationRepository.findByName(landSurveyDTO.classification())
                .orElseThrow(() -> new EntityNotFoundException("Classification not found for name: " + landSurveyDTO.classification()));
        Currency currency = currencyRepository.findByCode(landSurveyDTO.currency())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found for code: " + landSurveyDTO.currency()));

        Agency agency = agencyRepository.findByName(landSurveyDTO.agency())
                .orElseGet(() -> {
                    Agency newAgency = new Agency();
                    newAgency.setName(landSurveyDTO.agency());
                    return agencyRepository.save(newAgency);
                });

        Particular particular = particularRepository.findByName(landSurveyDTO.particular())
                .orElseGet(() -> {
                    Particular newParticular = new Particular();
                    newParticular.setName(landSurveyDTO.particular());
                    return particularRepository.save(newParticular);
                });

        Contact contact = contactRepository.findByPhone(landSurveyDTO.contact())
                .orElseGet(() -> {
                    Contact newContact = new Contact();
                    newContact.setPhone(landSurveyDTO.contact());
                    return contactRepository.save(newContact);
                });

        Folder folder = folderRepository.findByCode(landSurveyDTO.folder())
                .orElseGet(() -> {
                    Folder newFolder = new Folder();
                    newFolder.setCode(landSurveyDTO.folder());
                    return folderRepository.save(newFolder);
                });

        Locality locality = localityRepository.findByName(landSurveyDTO.locality())
                .orElseGet(() -> {
                    Locality newLocality = new Locality();
                    newLocality.setName(landSurveyDTO.locality());
                    return localityRepository.save(newLocality);
                });

        Section section = sectionRepository.findByName(landSurveyDTO.section())
                .orElseGet(() -> {
                    Section newSection = new Section();
                    newSection.setName(landSurveyDTO.section());
                    return sectionRepository.save(newSection);
                });

        Zone zone = zoneRepository.findByName(landSurveyDTO.zone())
                .orElseGet(() -> {
                    Zone newZone = new Zone();
                    newZone.setName(landSurveyDTO.zone());
                    return zoneRepository.save(newZone);
                });

        Title title = titleRepository.findBySituation(landSurveyDTO.title())
                .orElseGet(() -> {
                    Title newTitle = new Title();
                    newTitle.setSituation(landSurveyDTO.title());
                    return titleRepository.save(newTitle);
                });

        landSurvey.setAddress(landSurveyDTO.address());
        landSurvey.setCorner(landSurveyDTO.corner());
        landSurvey.setUnworkable(landSurveyDTO.unworkable());
        landSurvey.setTitle(title);
        landSurvey.setMeasurements(landSurveyDTO.measurements());
        landSurvey.setSurface(landSurveyDTO.surface());
        landSurvey.setObservation(landSurveyDTO.observation());
        landSurvey.setSurveyor(surveyor);
        landSurvey.setManager(manager);
        landSurvey.setFileType(fileType);
        landSurvey.setRoadType(roadType);
        landSurvey.setLocality(locality);
        landSurvey.setSection(section);
        landSurvey.setZone(zone);
        landSurvey.setSource(source);
        landSurvey.setClassification(classification);
        landSurvey.setAgency(agency);
        landSurvey.setParticular(particular);
        landSurvey.setContact(contact);
        landSurvey.setPrice(landSurveyDTO.price());
        landSurvey.setCurrency(currency);
        landSurvey.setFolder(folder);

        landSurvey = landSurveyRepository.save(landSurvey);

        for (AssessmentDTO assessment : landSurveyDTO.assessmentList()){
            UserSec assessor = userRepository.findUserEntityByEmail(assessment.assessor().value()).orElseThrow(EntityNotFoundException::new);
            landSurvey.addAssessment(Assessment.builder()
                    .id(new AssessmentId(assessor.getId(), landSurvey.getId()))
                    .assessor(assessor)
                    .price(assessment.price())
                    .currency(currencyRepository.findByCode(assessment.currency())
                            .orElseThrow(EntityNotFoundException::new))
                    .build());
        }

        return landSurvey;
    }

    @Override
    @Transactional
    public String deleteLandSurvey(Long id) {
        landSurveyRepository.deleteById(id);
        return "delete successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public LandSurveyDTO getLandSurveyById(Long id) {
        return adaptToDTO(landSurveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LandSurvey with id " + id + " not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LandSurveyDTO> searchLandSurveys(Integer minPrice,
                                                 Integer maxPrice,
                                                 String address,
                                                 String businessEvaluation,
                                                 String section,
                                                 String zone,
                                                 String agency,
                                                 String particular,
                                                 String classification,
                                                 String managerEmail,
                                                 String surveyorEmail,
                                                 String title,
                                                 Boolean rescinded,
                                                 String folder,
                                                 int pageNumber) {

        Long cheapFlag = settingService.getSettingByName("businessEvaluationCheapFlag");
        Long expensiveFlag = settingService.getSettingByName("businessEvaluationExpFlag");

        SearchLandSurveySpecification specification = new SearchLandSurveySpecification(
                minPrice,
                maxPrice,
                address,
                businessEvaluation,
                section,
                zone,
                agency,
                particular,
                classification,
                managerEmail,
                surveyorEmail,
                rescinded,
                folder,
                title,
                cheapFlag,
                expensiveFlag
        );

        Page<LandSurvey> landSurveyList = landSurveyRepository.findAll(specification, PageRequest.of(pageNumber, Integer.MAX_VALUE));
        return landSurveyList.map(this::adaptToDTO);
    }

    @Override
    public LandSurveyDTO updateLandSurvey(Long id, LandSurveyDTO newLandSurveyDTO) {

        LandSurvey landSurvey = landSurveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LandSurvey with id " + id + " not found"));

        UserSec surveyor = userRepository.findUserEntityByEmail(newLandSurveyDTO.surveyor().value())
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + newLandSurveyDTO.surveyor().value()));
        UserSec manager = userRepository.findUserEntityByEmail(newLandSurveyDTO.manager().value())
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + newLandSurveyDTO.manager().value()));
        FileType fileType = fileTypeRepository.findByName(newLandSurveyDTO.fileType())
                .orElseThrow(() -> new EntityNotFoundException("FileType not found for name: " + newLandSurveyDTO.fileType()));
        RoadType roadType = roadTypeRepository.findByName(newLandSurveyDTO.roadType())
                .orElseThrow(() -> new EntityNotFoundException("RoadType not found for name: " + newLandSurveyDTO.roadType()));
        Source source = sourceRepository.findByName(newLandSurveyDTO.source())
                .orElseThrow(() -> new EntityNotFoundException("Source not found for name: " + newLandSurveyDTO.source()));
        Classification classification = classificationRepository.findByName(newLandSurveyDTO.classification())
                .orElseThrow(() -> new EntityNotFoundException("Classification not found for name: " + newLandSurveyDTO.classification()));
        Currency currency = currencyRepository.findByCode(newLandSurveyDTO.currency())
                .orElseThrow(() -> new EntityNotFoundException("Currency not found for code: " + newLandSurveyDTO.currency()));
        Folder folder = folderRepository.findByCode(newLandSurveyDTO.folder())
                .orElseThrow(() -> new EntityNotFoundException("Folder not found for code: " + newLandSurveyDTO.folder()));

        LocalDateTime priceVerificationDate = newLandSurveyDTO.priceVerificationDate();

        Agency agency = agencyRepository.findByName(newLandSurveyDTO.agency())
                .orElseGet(() -> {
                    Agency newAgency = new Agency();
                    newAgency.setName(newLandSurveyDTO.agency());
                    return agencyRepository.save(newAgency);
                });

        Particular particular = particularRepository.findByName(newLandSurveyDTO.particular())
                .orElseGet(() -> {
                    Particular newParticular = new Particular();
                    newParticular.setName(newLandSurveyDTO.particular());
                    return particularRepository.save(newParticular);
                });

        Contact contact = contactRepository.findByPhone(newLandSurveyDTO.contact())
                .orElseGet(() -> {
                    Contact newContact = new Contact();
                    newContact.setPhone(newLandSurveyDTO.contact());
                    return contactRepository.save(newContact);
                });


        Locality locality = localityRepository.findByName(newLandSurveyDTO.locality())
                .orElseGet(() -> {
                    Locality newLocality = new Locality();
                    newLocality.setName(newLandSurveyDTO.locality());
                    return localityRepository.save(newLocality);
                });

        Section section = sectionRepository.findByName(newLandSurveyDTO.section())
                .orElseGet(() -> {
                    Section newSection = new Section();
                    newSection.setName(newLandSurveyDTO.section());
                    return sectionRepository.save(newSection);
                });

        Zone zone = zoneRepository.findByName(newLandSurveyDTO.zone())
                .orElseGet(() -> {
                    Zone newZone = new Zone();
                    newZone.setName(newLandSurveyDTO.zone());
                    return zoneRepository.save(newZone);
                });

        Title title = titleRepository.findBySituation(newLandSurveyDTO.title())
                .orElseGet(() -> {
                    Title newTitle = new Title();
                    newTitle.setSituation(newLandSurveyDTO.title());
                    return  titleRepository.save(newTitle);
                });

        landSurvey.setAddress(newLandSurveyDTO.address());
        landSurvey.setCorner(newLandSurveyDTO.corner());
        landSurvey.setUnworkable(newLandSurveyDTO.unworkable());
        landSurvey.setTitle(title);
        landSurvey.setMeasurements(newLandSurveyDTO.measurements());
        landSurvey.setSurface(newLandSurveyDTO.surface());
        landSurvey.setPriceVerificationDate(priceVerificationDate);
        landSurvey.setObservation(newLandSurveyDTO.observation());
        landSurvey.setSurveyor(surveyor);
        landSurvey.setManager(manager);
        landSurvey.setFileType(fileType);
        landSurvey.setRoadType(roadType);
        landSurvey.setLocality(locality);
        landSurvey.setSection(section);
        landSurvey.setZone(zone);
        landSurvey.setSource(source);
        landSurvey.setClassification(classification);
        landSurvey.setAgency(agency);
        landSurvey.setParticular(particular);
        landSurvey.setContact(contact);
        landSurvey.setPrice(newLandSurveyDTO.price());
        landSurvey.setCurrency(currency);
        landSurvey.setFolder(folder);
        landSurvey.setReassessmentDate(newLandSurveyDTO.reassessmentDate());

        landSurvey.getAssessmentList().clear();
        for (AssessmentDTO assessment : newLandSurveyDTO.assessmentList()){
            UserSec assessor = userRepository.findUserEntityByEmail(assessment.assessor().value()).orElseThrow(EntityNotFoundException::new);
            landSurvey.addAssessment(Assessment.builder()
                    .id(new AssessmentId(assessor.getId(), landSurvey.getId()))
                    .assessor(assessor)
                    .price(assessment.price())
                    .currency(currencyRepository.findByCode(assessment.currency())
                            .orElseThrow(EntityNotFoundException::new))
                    .build());
        }

        return adaptToDTO(saveSecure(landSurvey));
    }

    public LandSurveyDTO adaptToDTO(LandSurvey landSurvey){

        List<AssessmentDTO> assessmentList = landSurvey.getAssessmentList()
                .stream()
                .map(assessmentService::adaptToDTO)
                .toList();

        return new LandSurveyDTO(landSurvey.getId(),
                landSurvey.getCreationDate(),
                landSurvey.getAddress(),
                landSurvey.getCorner(),
                landSurvey.getUnworkable(),
                landSurvey.getTitle() != null ? landSurvey.getTitle().getSituation() : null,
                landSurvey.getMeasurements(),
                landSurvey.getSurface(),
                landSurvey.getPriceVerificationDate(),
                landSurvey.getReassessmentDate(),
                landSurvey.getObservation(),
                landSurvey.getIsRescinded(),
                landSurvey.getIsArchived(),
                userService.userToOption(landSurvey.getSurveyor()),
                userService.userToOption(landSurvey.getManager()),
                landSurvey.getFileType() != null ? landSurvey.getFileType().getName() : null,
                landSurvey.getRoadType() != null ? landSurvey.getRoadType().getName() : null,
                landSurvey.getLocality() != null ? landSurvey.getLocality().getName() : null,
                landSurvey.getSection() != null ? landSurvey.getSection().getName() : null,
                landSurvey.getZone() != null ? landSurvey.getZone().getName() : null,
                landSurvey.getSource() != null ? landSurvey.getSource().getName() : null,
                landSurvey.getClassification() != null ? landSurvey.getClassification().getName() : null,
                landSurvey.getAgency() != null ? landSurvey.getAgency().getName() : null,
                landSurvey.getParticular() != null ? landSurvey.getParticular().getName() : null,
                landSurvey.getContact() != null ? landSurvey.getContact().getPhone() : null,
                landSurvey.getPrice(),
                landSurvey.getCurrency() != null ? landSurvey.getCurrency().getCode() : null,
                landSurvey.getFolder() != null ? landSurvey.getFolder().getCode() : null,
                assessmentList);
    }

    @Override
    @Transactional
    public Boolean switchRescind(Long id) {
        LandSurvey landSurvey = landSurveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relevamiento con id " + id + " no encontrado"));
        landSurvey.setIsRescinded(!landSurvey.getIsRescinded());
        landSurvey.setFolder(null);
        saveSecure(landSurvey);
        return landSurvey.getIsRescinded();
    }

    @Override
    @Transactional
    public LandSurvey saveSecure(LandSurvey landSurvey) {

        /*if (landSurvey.getAssessmentList().size() < 2){
            throw new IllegalArgumentException("Debe haber al menos dos Assessment para un LandSurvey.");
        }*/

        validateAssessmentDeviation(landSurvey);


        return landSurveyRepository.save(landSurvey);
    }

    public void validateAssessmentDeviation(LandSurvey landSurvey) {
        List<Assessment> assessments = landSurvey.getAssessmentList();
        if (assessments == null || assessments.isEmpty()) return;

        Double currentRate = currencyRepository.findByCode("USD")
                .orElseThrow(EntityNotFoundException::new)
                .getExchangeReference();

        double minPriceInUsd = Double.MAX_VALUE;
        double maxPriceInUsd = Double.MIN_VALUE;

        for (Assessment assessment : assessments) {
            if (assessment.getPrice() == null || assessment.getCurrency() == null) continue;

            double price = assessment.getPrice();
            String currencyCode = assessment.getCurrency().getCode();

            double priceInUsd;
            if ("USD".equalsIgnoreCase(currencyCode)) {
                priceInUsd = price;
            } else if ("ARS".equalsIgnoreCase(currencyCode)) {
                priceInUsd = price / currentRate;
            } else {
                throw new IllegalArgumentException("Unsupported currency: " + currencyCode);
            }

            minPriceInUsd = Math.min(minPriceInUsd, priceInUsd);
            maxPriceInUsd = Math.max(maxPriceInUsd, priceInUsd);
        }

        // Ensure we have at least two prices to compare
        if (minPriceInUsd == Double.MAX_VALUE || maxPriceInUsd == Double.MIN_VALUE || minPriceInUsd == 0) return;

        double deviation = ((maxPriceInUsd / minPriceInUsd) - 1) * 100;

        Long maxDeviation = Long.parseLong(settingRepository.findById("maxDeviation")
                .orElse(null)
                .getValue());

        if (deviation > maxDeviation) {
            throw new IllegalArgumentException("Se superó la desviación máxima permitida.");
        }
    }

}
