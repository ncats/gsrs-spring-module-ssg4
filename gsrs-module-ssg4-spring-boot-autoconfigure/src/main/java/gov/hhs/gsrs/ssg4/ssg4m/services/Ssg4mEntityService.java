package gov.hhs.gsrs.ssg4.ssg4m.services;

import gov.hhs.gsrs.ssg4.ssg4m.models.*;
import gov.hhs.gsrs.ssg4.ssg4m.repositories.Ssg4mRepository;
/*
import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;
*/

import ix.core.validator.ValidationMessage;
import ix.core.validator.ValidationResponse;
import ix.core.validator.Validator;
import ix.core.validator.ValidatorCallback;
import ix.core.validator.ValidatorCategory;
import ix.ginas.utils.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class Ssg4mEntityService {
    public static final String CONTEXT = "ssg4m";

    public Ssg4mEntityService() {
    }

    @Autowired
    private Ssg4mRepository repository;

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
            return repository.saveAndFlush(ssg4mSyntheticPathway);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Transactional
    public Ssg4mSyntheticPathway update(Ssg4mSyntheticPathway ssg4mSyntheticPathway) {
        return repository.saveAndFlush(ssg4mSyntheticPathway);
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

    public Optional<Ssg4mSyntheticPathway> get(Long id) {
        return repository.findById(id);
    }

    public Optional<Ssg4mSyntheticPathway> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(Long.parseLong(someKindOfId));
    }

    protected Optional<Long> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<Ssg4mSyntheticPathway> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().synthPathwaySkey);
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
