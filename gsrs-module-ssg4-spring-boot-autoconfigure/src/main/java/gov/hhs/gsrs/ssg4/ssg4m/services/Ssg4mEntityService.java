package gov.hhs.gsrs.ssg4.ssg4m.services;

import gov.hhs.gsrs.ssg4.ssg4m.models.*;
import gov.hhs.gsrs.ssg4.ssg4m.repositories.*;
import gov.hhs.gsrs.ssg4.Util;

/*
import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;
import ix.utils.Util;
import ix.core.validator.ValidationMessage;
import ix.core.validator.ValidationResponse;
import ix.core.validator.Validator;
import ix.core.validator.ValidatorCallback;
import ix.core.validator.ValidatorCategory;
import ix.ginas.utils.validation.ValidatorFactory;
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Ssg4mEntityService {
    public static final String CONTEXT = "ssg4m";

    public Ssg4mEntityService() {
    }

    @Autowired
    private Ssg4mRepository repository;

    @Autowired
    private Ssg4mSyntheticPathwayIndexRepository repositoryIndex;

    @Autowired
    private ObjectMapper objectMapper;

    //@Autowired
    //private GroupRepository groupRepository;

    public Class<Ssg4mSyntheticPathway> getEntityClass() {
        return Ssg4mSyntheticPathway.class;
    }

    public Long parseIdFromString(String idAsString) {
        return Long.parseLong(idAsString);
    }

    protected Ssg4mSyntheticPathway fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Ssg4mSyntheticPathway.class);
    }

    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Ssg4mSyntheticPathway create(Ssg4mSyntheticPathway ssg4mSyntheticPathway) {
        try {
            if (ssg4mSyntheticPathway.synthPathwayId == null) {
                ssg4mSyntheticPathway.synthPathwayId = UUID.randomUUID();
            }
            return repository.saveAndFlush(ssg4mSyntheticPathway);
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

    @Transactional
    public Ssg4mSyntheticPathway update(Ssg4mSyntheticPathway ssg4mSyntheticPathway) {
        // Update/Save existing data into Synthetic Pathway table
        Ssg4mSyntheticPathway ssg4SynthPathSaved = repository.saveAndFlush(ssg4mSyntheticPathway);

        if (ssg4SynthPathSaved != null) {
            // Delete existing records from Index table
            repositoryIndex.findBySynthPathwaySkey(ssg4SynthPathSaved.synthPathwaySkey).forEach(ind -> {
                repositoryIndex.deleteById(ind.synthPathwayDetailSkey);
            });

            // Create/Inert new records into the Index table
            createPathwayIndex(ssg4SynthPathSaved);

        }
        return ssg4SynthPathSaved;
    }

    public List<Ssg4mSyntheticPathwayDetail> findBySyntheticDetailBySubUuid(String subUuid) {
        List<Ssg4mSyntheticPathwayDetail> list = repository.findBySyntheticDetailBySubUuid(subUuid);
        return list;
    }

    // public List<Ssg4mSyntheticPathwayDetail> createPathwayIndex(Ssg4mSyntheticPathway synthPathway) {
    public void createPathwayIndex(Ssg4mSyntheticPathway synthPathway) {
        JsonNode jsonNode = null;
        Ssg4mSyntheticPathwayDetail ssg4mSyntheticPathwayDetail = new Ssg4mSyntheticPathwayDetail();

        try {
            // Get Json from clob
            jsonNode = synthPathway.getSbmsnDataJson();

            JsonNode jsonSubG4m = jsonNode.path("specifiedSubstanceG4m");

            // Get Parent Substance
            JsonNode jsonParentSub = jsonSubG4m.path("parentSubstance");
            try {
                // create Index Object to store into the database
                Ssg4mSyntheticPathwayDetail pathwayIndex = new Ssg4mSyntheticPathwayDetail();

                // Set foreign Key synthPathwaySkey
                pathwayIndex.synthPathwaySkey = synthPathway.synthPathwaySkey;

                // Get Parent Substance Details
                pathwayIndex.sbstncUuid = jsonParentSub.findPath("refuuid").textValue();
                pathwayIndex.sbstncPfrdNm = jsonParentSub.findPath("refPname").textValue();

                pathwayIndex.sbstncReactnSectNm = "PARENT SUBSTANCE";

                // Create/Save Parent Substance into database
                Ssg4mSyntheticPathwayDetail pathwayIndexSaved = repositoryIndex.saveAndFlush(pathwayIndex);
                /*if (pathwayIndexSaved == null) {

                }*/
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Get SSG4m Process
            JsonNode jsonProcessList = jsonSubG4m.path("process");
            if (!jsonProcessList.isMissingNode()) {
                for (JsonNode jsonProcess : jsonProcessList) {

                    // Get Site
                    JsonNode jsonSiteList = jsonProcess.path("sites");
                    if (!jsonSiteList.isMissingNode()) {
                        for (JsonNode jsonSite : jsonSiteList) {

                            // Get Stage
                            JsonNode jsonStageList = jsonSite.path("stages");
                            if (!jsonStageList.isMissingNode()) {
                                for (JsonNode jsonStage : jsonStageList) {

                                    // Get Starting Materials
                                    JsonNode jsonStartingMaterialList = jsonStage.path("startingMaterials");
                                    if (!jsonStartingMaterialList.isMissingNode()) {
                                        for (JsonNode jsonStartingMaterial : jsonStartingMaterialList) {

                                            try {
                                                // create Index Object to store into the database
                                                Ssg4mSyntheticPathwayDetail pathwayIndex = new Ssg4mSyntheticPathwayDetail();

                                                // Set foreign Key synthPathwaySkey
                                                pathwayIndex.synthPathwaySkey = synthPathway.synthPathwaySkey;

                                                // Get Substance Name Details
                                                JsonNode jsonMaterialSubName = jsonStartingMaterial.path("substanceName");

                                                pathwayIndex.sbstncUuid = jsonMaterialSubName.findPath("refuuid").textValue();
                                                pathwayIndex.sbstncPfrdNm = jsonMaterialSubName.findPath("refPname").textValue();

                                                pathwayIndex.sbstncRoleNm = jsonStartingMaterial.findPath("substanceRole").textValue();
                                                pathwayIndex.sbstncReactnSectNm = "STARTING MATERIAL";

                                                // Create/Save Starting Material into database
                                                Ssg4mSyntheticPathwayDetail pathwayIndexSaved = repositoryIndex.saveAndFlush(pathwayIndex);
                                                /*if (pathwayIndexSaved == null) {

                                                }*/

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        } // for Starting Materials
                                    }

                                    // Get Process Materials
                                    JsonNode jsonProcessingMaterialsList = jsonStage.path("processingMaterials");
                                    if (!jsonProcessingMaterialsList.isMissingNode()) {
                                        for (JsonNode jsonProcessingMaterial : jsonProcessingMaterialsList) {

                                            try {
                                                // create Index Object to store into the database
                                                Ssg4mSyntheticPathwayDetail pathwayIndex = new Ssg4mSyntheticPathwayDetail();

                                                // Set foreign Key synthPathwaySkey
                                                pathwayIndex.synthPathwaySkey = synthPathway.synthPathwaySkey;

                                                // Get Substance Name Details
                                                JsonNode jsonMaterialSubName = jsonProcessingMaterial.path("substanceName");

                                                pathwayIndex.sbstncUuid = jsonMaterialSubName.findPath("refuuid").textValue();
                                                pathwayIndex.sbstncPfrdNm = jsonMaterialSubName.findPath("refPname").textValue();

                                                pathwayIndex.sbstncRoleNm = jsonProcessingMaterial.findPath("substanceRole").textValue();
                                                pathwayIndex.sbstncReactnSectNm = "PROCESSING MATERIAL";

                                                // Create/Save Processing Material into database
                                                Ssg4mSyntheticPathwayDetail pathwayIndexSaved = repositoryIndex.saveAndFlush(pathwayIndex);
                                                /*if (pathwayIndexSaved == null) {

                                                }*/

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        } // for Processing Materials
                                    }

                                    // Get Resulting Materials
                                    JsonNode jsonResultingMaterialsList = jsonStage.path("resultingMaterials");
                                    if (!jsonResultingMaterialsList.isMissingNode()) {
                                        for (JsonNode jsonResultingMaterial : jsonResultingMaterialsList) {

                                            try {
                                                // create Index Object to store into the database
                                                Ssg4mSyntheticPathwayDetail pathwayIndex = new Ssg4mSyntheticPathwayDetail();

                                                // Set foreign Key synthPathwaySkey
                                                pathwayIndex.synthPathwaySkey = synthPathway.synthPathwaySkey;

                                                // Get Substance Name Details
                                                JsonNode jsonMaterialSubName = jsonResultingMaterial.path("substanceName");

                                                pathwayIndex.sbstncUuid = jsonMaterialSubName.findPath("refuuid").textValue();
                                                pathwayIndex.sbstncPfrdNm = jsonMaterialSubName.findPath("refPname").textValue();

                                                pathwayIndex.sbstncRoleNm = jsonResultingMaterial.findPath("substanceRole").textValue();
                                                pathwayIndex.sbstncReactnSectNm = "RESULTING MATERIAL";

                                                // Create/Save Resulting Material into database
                                                Ssg4mSyntheticPathwayDetail pathwayIndexSaved = repositoryIndex.saveAndFlush(pathwayIndex);
                                                /*if (pathwayIndexSaved == null) {

                                                }*/

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        } // for Resulting Materials
                                    }

                                } // for Stage
                            } // Process List not null
                        } // for Site
                    } // Site List not null
                } // for Process
            } // Process List not null
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //@Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    //@Override
    //protected AbstractEntityUpdatedEvent<Ssg4mSyntheticPathway> newUpdateEvent(Ssg4mSyntheticPathway updatedEntity) {
    //    return null;
    // }

    //@Override
    //protected AbstractEntityCreatedEvent<Ssg4mSyntheticPathway> newCreationEvent(Ssg4mSyntheticPathway createdEntity) {
    //    return null;
    //}

    public Long getIdFrom(Ssg4mSyntheticPathway entity) {
        return entity.getId();
    }

    protected List<Ssg4mSyntheticPathway> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }

    protected Ssg4mSyntheticPathway fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, Ssg4mSyntheticPathway.class);
    }

    protected List<Ssg4mSyntheticPathway> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    protected JsonNode toJson(Ssg4mSyntheticPathway ssg4mSyntheticPathway) throws IOException {
        return objectMapper.valueToTree(ssg4mSyntheticPathway);
    }

    public long count() {
        return repository.count();
    }

    public Optional<Ssg4mSyntheticPathway> get(String id) {
        // return repository.findById(id);
        return flexLookupIdOnly(id);
    }

    public Optional<Ssg4mSyntheticPathway> flexLookup(String someKindOfId) {
        if (Util.isUUID(someKindOfId)) {
            UUID pathwayId = UUID.fromString(someKindOfId);
            return repository.findBySynthPathwayId(pathwayId);
        }
        if (someKindOfId == null) {
            return Optional.empty();
        }
        return repository.findById(Long.parseLong(someKindOfId));
    }

    protected Optional<Ssg4mSyntheticPathway> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<Ssg4mSyntheticPathway> synthPathway = flexLookup(someKindOfId);
        if (synthPathway.isPresent()) {
            return synthPathway;
           // return Optional.of(found.get().synthPathwaySkey);
        }
        return Optional.empty();
    }

    /*
    public ValidationResponse<Ssg4mSyntheticPathway> validateEntity(JsonNode updatedEntityJson) throws Exception {

        Ssg4mSyntheticPathway updatedEntity = fromUpdatedJson(updatedEntityJson);
        //updatedEntity should have the same id
        Long id = getIdFrom(updatedEntity);

        //Optional.ofNullable(id).map(id->get(id)); ?
        Optional<Ssg4mSyntheticPathway> opt = (id==null ? Optional.empty() : get(id));

        Validator<Ssg4mSyntheticPathway> validator;
        ValidationResponse<Ssg4mSyntheticPathway> response;
        ValidatorCallback callback;

        //If it's an update
        if(opt.isPresent()){
            validator  = validatorFactory.getSync().createValidatorFor(updatedEntity, opt.get(), DefaultValidatorConfig.METHOD_TYPE.UPDATE, cat);
            response = createValidationResponse(updatedEntity, opt.orElse(null), DefaultValidatorConfig.METHOD_TYPE.UPDATE);
            callback = createCallbackFor(updatedEntity, response, DefaultValidatorConfig.METHOD_TYPE.UPDATE);
            validator.validate(updatedEntity, opt.orElse(null), callback);

            //If it's new (insert)
        }else{
            validator  = validatorFactory.getSync().createValidatorFor(updatedEntity, null, DefaultValidatorConfig.METHOD_TYPE.CREATE, cat);
            response = createValidationResponse(updatedEntity, opt.orElse(null), DefaultValidatorConfig.METHOD_TYPE.CREATE);
            callback = createCallbackFor(updatedEntity, response, DefaultValidatorConfig.METHOD_TYPE.CREATE);
            validator.validate(updatedEntity, opt.orElse(null), callback);
        }
        callback.complete();

        //always send 200 even if validation has errors?
        return response;
    }*/
}
