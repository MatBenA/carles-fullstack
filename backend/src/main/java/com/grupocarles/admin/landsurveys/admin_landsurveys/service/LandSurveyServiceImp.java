package com.grupocarles.admin.landsurveys.admin_landsurveys.service;

import java.util.ArrayList;
import java.util.Comparator;
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

    @Override
    public LandSurvey createLandSurvey(LandSurveyDTO landSurveyDTO) {

        LandSurvey landSurvey = DTOToLandSurvey(landSurveyDTO);
        landSurvey.setIsRescinded(false);
        landSurvey.setIsArchived(false);

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

        return saveSecure(landSurvey);
    }

    private LandSurvey DTOToLandSurvey(LandSurveyDTO landSurvey) {

        UserSec surveyor = userRepository.findUserEntityByEmail(landSurvey.surveyor().value()).orElseThrow(EntityNotFoundException::new);
        UserSec manager = userRepository.findUserEntityByEmail(landSurvey.manager().value()).orElseThrow(EntityNotFoundException::new);
        FileType fileType = fileTypeRepository.findByName(landSurvey.fileType()).orElseThrow(EntityNotFoundException::new);
        RoadType roadType = roadTypeRepository.findByName(landSurvey.roadType()).orElseThrow(EntityNotFoundException::new);
        Locality locality = localityRepository.findByName(landSurvey.locality()).orElseThrow(EntityNotFoundException::new);
        Section section = sectionRepository.findByName(landSurvey.section()).orElseThrow(EntityNotFoundException::new);
        Zone zone = zoneRepository.findByName(landSurvey.zone()).orElseThrow(EntityNotFoundException::new);
        Source source = sourceRepository.findByName(landSurvey.source()).orElseThrow(EntityNotFoundException::new);
        Classification classification = classificationRepository.findByName(landSurvey.classification()).orElseThrow(EntityNotFoundException::new);
        Currency currency = currencyRepository.findByCode(landSurvey.currency()).orElseThrow(EntityNotFoundException::new);

        Agency agency;
        if (agencyRepository.findByName(landSurvey.agency()).isEmpty()) {
            Agency newAgency = new Agency();
            newAgency.setName(landSurvey.agency());
            agency = agencyRepository.save(newAgency);
        } else {
            agency = agencyRepository.findByName(landSurvey.agency()).orElse(new Agency(landSurvey.agency()));
        }


        Particular particular = particularRepository.findByName(landSurvey.particular()).orElseThrow(EntityNotFoundException::new);
        Contact contact = contactRepository.findByPhone(landSurvey.contact()).orElseThrow(EntityNotFoundException::new);

        return LandSurvey.builder()
                .address(landSurvey.address())
                .corner(landSurvey.corner())
                .title(landSurvey.title())
                .titleSituation(landSurvey.titleSituation())
                .measurements(landSurvey.measurements())
                .surface(landSurvey.surface())
                .observation(landSurvey.observation())
                .surveyor(surveyor)
                .manager(manager)
                .fileType(fileType)
                .roadType(roadType)
                .locality(locality)
                .section(section)
                .zone(zone)
                .source(source)
                .classification(classification)
                .agency(agency)
                .particular(particular)
                .contact(contact)
                .price(landSurvey.price())
                .currency(currency)
                .build();
    }

    @Override
    public void deleteLandSurvey(long id) {
        landSurveyRepository.deleteById(id);
    }

    @Override
    public Page<LandSurveyDTO> getPaginatedLandSurveys(Pageable pageable) {
        return landSurveyRepository.findAll(pageable).map(this::adaptToDTO);
    }

    @Override
    public LandSurveyDTO getLandSurveyById(long id) {
        return adaptToDTO(landSurveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + id + " not found")));
    }

    @Override
    public List<LandSurveyDTO> getAllLandSurveys() {
        return landSurveyRepository.findAll().stream().map(this::adaptToDTO).toList();
    }

    @Override
    public Page<LandSurveyDTO> searchLandSurveys(Integer minPrice,
                                                 Integer maxPrice,
                                                 String businessEvaluation,
                                                 String section,
                                                 String zone,
                                                 String agency,
                                                 String particular,
                                                 String classification,
                                                 Boolean title,
                                                 int pageNumber) {

        Long cheapFlag = settingService.getSettingByName("businessEvaluationCheapFlag");
        Long expensiveFlag = settingService.getSettingByName("businessEvaluationExpFlag");

        SearchLandSurveySpecification specification = new SearchLandSurveySpecification(
                minPrice,
                maxPrice,
                businessEvaluation,
                section,
                zone,
                agency,
                particular,
                classification,
                title,
                cheapFlag,
                expensiveFlag
        );


        return landSurveyRepository
                .findAll(specification, PageRequest.of(pageNumber, 100))
                .map(this::adaptToDTO);
    }

    @Override
    public LandSurveyDTO updateLandSurvey(long id, LandSurveyDTO newLandSurvey) {

        LandSurvey updateLandSurvey = landSurveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("LandSurvey with id " + id + " not found"));

        updateLandSurvey.setAddress(newLandSurvey.address());
        updateLandSurvey.setCorner(newLandSurvey.corner());
        updateLandSurvey.setTitle(newLandSurvey.title());
        updateLandSurvey.setTitleSituation(newLandSurvey.titleSituation());
        updateLandSurvey.setMeasurements(newLandSurvey.measurements());
        updateLandSurvey.setSurface(newLandSurvey.surface());
        updateLandSurvey.setPriceVerificationDate(newLandSurvey.priceVerificationDate());
        updateLandSurvey.setReassessmentDate(newLandSurvey.reassessmentDate());
        updateLandSurvey.setObservation(newLandSurvey.observation());
        updateLandSurvey.setPrice(newLandSurvey.price());

        updateLandSurvey.setSurveyor(userRepository.findUserEntityByEmail(newLandSurvey.surveyor().value()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setManager(userRepository.findUserEntityByEmail(newLandSurvey.manager().value()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setFileType(fileTypeRepository.findByName(newLandSurvey.fileType()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setRoadType(roadTypeRepository.findByName(newLandSurvey.roadType()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setLocality(localityRepository.findByName(newLandSurvey.locality()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setSection(sectionRepository.findByName(newLandSurvey.section()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setZone(zoneRepository.findByName(newLandSurvey.zone()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setSource(sourceRepository.findByName(newLandSurvey.source()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setClassification(classificationRepository.findByName(newLandSurvey.classification()).orElseThrow(EntityNotFoundException::new));
        updateLandSurvey.setCurrency(currencyRepository.findByCode(newLandSurvey.currency()).orElseThrow(EntityNotFoundException::new));


        updateLandSurvey.setAgency(agencyRepository.findByName(newLandSurvey.agency()).orElse(new Agency(newLandSurvey.agency())));
        updateLandSurvey.setParticular(particularRepository.findByName(newLandSurvey.particular()).orElse(null));
        updateLandSurvey.setContact(contactRepository.findByPhone(newLandSurvey.contact()).orElse(null));

        List<Assessment> newAssessmentList = new ArrayList<>();
        newLandSurvey.assessmentList().forEach(assessmentOneDTO ->
                newAssessmentList.add(assessmentService.convertFromDTO(assessmentOneDTO, updateLandSurvey)));
        updateLandSurvey.getAssessmentList().clear();
        updateLandSurvey.getAssessmentList().addAll(newAssessmentList);

        return adaptToDTO(saveSecure(updateLandSurvey));
    }

    @Override
    public LandSurveyDTO adaptToDTO(LandSurvey landSurvey){

        Long repricing = Long.parseLong(
                settingRepository.findById("rePricingPercentaje")
                        .orElseThrow(() -> new EntityNotFoundException("re pricing setting not found"))
                        .getValue());

        List<AssessmentDTO> assessmentList = landSurvey.getAssessmentList()
                .stream()
                .map(assessment -> assessmentService.adaptToDTO(assessment))
                .toList();

        if(landSurvey.getAgency() == null){ landSurvey.setAgency(new Agency()); }
        if(landSurvey.getParticular() == null){ landSurvey.setParticular(new Particular()); }
        if(landSurvey.getContact() == null){ landSurvey.setContact(new Contact()); }
        if(landSurvey.getParticular() == null){ landSurvey.setParticular(new Particular()); }
        if(landSurvey.getZone() == null){ landSurvey.setZone(new Zone()); }
        if(landSurvey.getManager() == null){ landSurvey.setManager(new UserSec()); }
        if(landSurvey.getSource() == null){ landSurvey.setSource(new Source()); }
        if(landSurvey.getSection() == null){ landSurvey.setSection(new Section()); }

        return new LandSurveyDTO(landSurvey.getId(),
                landSurvey.getCreationDate(),
                landSurvey.getAddress(),
                landSurvey.getCorner(),
                landSurvey.getTitle(),
                landSurvey.getTitleSituation(),
                landSurvey.getMeasurements(),
                landSurvey.getSurface(),
                landSurvey.getPriceVerificationDate(),
                landSurvey.getReassessmentDate(),
                landSurvey.getObservation(),
                landSurvey.getIsRescinded(),
                landSurvey.getIsArchived(),
                userService.userToOption(landSurvey.getSurveyor()),
                userService.userToOption(landSurvey.getManager()),
                landSurvey.getFileType().getName(),
                landSurvey.getRoadType().getName(),
                landSurvey.getLocality().getName(),
                landSurvey.getSection().getName(),
                landSurvey.getZone().getName(),
                landSurvey.getSource().getName(),
                landSurvey.getClassification().getName(),
                landSurvey.getAgency().getName(),
                landSurvey.getParticular().getName(),
                landSurvey.getContact().getPhone(),
                (landSurvey.getPrice() * repricing),
                landSurvey.getCurrency().getCode(),
                assessmentList);
    }

    @Override
    public Boolean switchRescind(Long id) {
        LandSurvey landSurvey = landSurveyRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relevamiento con id " + id + " no encontrado"));
        landSurvey.setIsRescinded(!landSurvey.getIsRescinded());
        saveSecure(landSurvey);
        return landSurvey.getIsRescinded();
    }

    @Override
    public List<LandSurveyDTO> getRescinds() {
        return landSurveyRepository
                .findByIsRescinded(true)
                .stream()
                .map(this::adaptToDTO).toList();
    }

    @Override
    public LandSurvey saveSecure(LandSurvey landSurvey) {

        if (landSurvey.getAssessmentList().size() < 2){
            throw new IllegalArgumentException("Debe haber al menos dos Assessment para un LandSurvey.");
        }

        Long maxPriceAssessment = landSurvey.getAssessmentList().stream()
                .max(Comparator.comparingLong(Assessment::getPrice))
                .get()
                .getPrice();

        Long minPriceAssessment = landSurvey.getAssessmentList().stream()
                .min(Comparator.comparingLong(Assessment::getPrice))
                .get()
                .getPrice();

        Double deviation = ((double)maxPriceAssessment / minPriceAssessment - 1) * 100;

        Long maxDeviation = Long.parseLong(settingRepository.findById("maxDeviation").orElse(null).getValue());

        if (deviation > maxDeviation) {
            throw new IllegalArgumentException("Se supero la desviacion maxima.");
        }

        return landSurveyRepository.save(landSurvey);
    }
}
