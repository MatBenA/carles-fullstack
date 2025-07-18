package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.time.LocalDateTime;
import java.util.List;

import com.grupocarles.admin.landsurveys.admin_landsurveys.dto.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.model.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.repository.*;
import com.grupocarles.admin.landsurveys.admin_landsurveys.specification.SearchLandSurveySpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LandSurveyServiceImp implements LandSurveyService {

    @Autowired
    private LandSurveyRepository landSurveyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private FileTypeRepository fileTypeRepository;

    @Autowired
    private RoadTypeRepository roadTypeRepository;

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private ParticularRepository particularRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private SettingService settingService;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Override
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
        UserSec surveyor = userRepository.findUserEntityByEmail(landSurveyDTO.surveyor().value()).orElseThrow(EntityNotFoundException::new);
        UserSec manager = userRepository.findUserEntityByEmail(landSurveyDTO.manager().value()).orElseThrow(EntityNotFoundException::new);
        FileType fileType = fileTypeRepository.findByName(landSurveyDTO.fileType()).orElseThrow(EntityNotFoundException::new);
        RoadType roadType = roadTypeRepository.findByName(landSurveyDTO.roadType()).orElseThrow(EntityNotFoundException::new);
        Source source = sourceRepository.findByName(landSurveyDTO.source()).orElseThrow(EntityNotFoundException::new);
        Classification classification = classificationRepository.findByName(landSurveyDTO.classification()).orElseThrow(EntityNotFoundException::new);
        Currency currency = currencyRepository.findByCode(landSurveyDTO.currency()).orElseThrow(EntityNotFoundException::new);

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
    public String deleteLandSurvey(Long id) {
        landSurveyRepository.deleteById(id);
        return "delete successfully";
    }

    @Override
    public LandSurveyDTO getLandSurveyById(Long id) {
        return adaptToDTO(landSurveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LandSurvey with id " + id + " not found")));
    }

    @Override
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

        LandSurvey landSurvey = landSurveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LandSurvey with id " + id + " not found"));

        UserSec surveyor = userRepository.findUserEntityByEmail(newLandSurveyDTO.surveyor().value()).orElseThrow(EntityNotFoundException::new);
        UserSec manager = userRepository.findUserEntityByEmail(newLandSurveyDTO.manager().value()).orElseThrow(EntityNotFoundException::new);
        FileType fileType = fileTypeRepository.findByName(newLandSurveyDTO.fileType()).orElseThrow(EntityNotFoundException::new);
        RoadType roadType = roadTypeRepository.findByName(newLandSurveyDTO.roadType()).orElseThrow(EntityNotFoundException::new);
        Source source = sourceRepository.findByName(newLandSurveyDTO.source()).orElseThrow(EntityNotFoundException::new);
        Classification classification = classificationRepository.findByName(newLandSurveyDTO.classification()).orElseThrow(EntityNotFoundException::new);
        Currency currency = currencyRepository.findByCode(newLandSurveyDTO.currency()).orElseThrow(EntityNotFoundException::new);
        Folder folder = folderRepository.findByCode(newLandSurveyDTO.folder()).orElseThrow(EntityNotFoundException::new);

        LocalDateTime priceVerificationDate;
        if (landSurvey.getPrice() != newLandSurveyDTO.price()){
            priceVerificationDate = LocalDateTime.now();
        } else {
            priceVerificationDate = newLandSurveyDTO.priceVerificationDate();
        }

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
    public Boolean switchRescind(Long id) {
        LandSurvey landSurvey = landSurveyRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relevamiento con id " + id + " no encontrado"));
        landSurvey.setIsRescinded(!landSurvey.getIsRescinded());
        landSurvey.setFolder(null);
        saveSecure(landSurvey);
        return landSurvey.getIsRescinded();
    }

    @Override
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
