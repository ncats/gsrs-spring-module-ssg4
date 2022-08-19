package gov.hhs.gsrs.ssg4.ssg4m.services;

import gov.hhs.gsrs.ssg4.ssg4m.models.*;
import gov.hhs.gsrs.ssg4.ssg4m.repositories.Ssg4mRepository;

import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;

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
public class Ssg4mEntityService extends AbstractGsrsEntityService<Ssg4mSyntheticPathway, Long> {
    public static final String  CONTEXT = "ssg4m";

    public Ssg4mEntityService() {
        super(CONTEXT,  IdHelpers.NUMBER, null, null, null);
    }

    @Autowired
    private Ssg4mRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Class<Ssg4mSyntheticPathway> getEntityClass() {
        return Ssg4mSyntheticPathway.class;
    }

    @Override
    public Long parseIdFromString(String idAsString) {
        return Long.parseLong(idAsString);
    }

    @Override
    protected Ssg4mSyntheticPathway fromNewJson(JsonNode json) throws IOException {
        return objectMapper.convertValue(json, Ssg4mSyntheticPathway.class);
    }

    @Override
    public Page page(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    protected Ssg4mSyntheticPathway create(Ssg4mSyntheticPathway ssg4mSyntheticPathway) {
        try {
            return repository.saveAndFlush(ssg4mSyntheticPathway);
        }catch(Throwable t){
            t.printStackTrace();
            throw t;
        }
    }

    @Override
    @Transactional
    protected Ssg4mSyntheticPathway update(Ssg4mSyntheticPathway ssg4mSyntheticPathway) {
        return repository.saveAndFlush(ssg4mSyntheticPathway);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    protected AbstractEntityUpdatedEvent<Ssg4mSyntheticPathway> newUpdateEvent(Ssg4mSyntheticPathway updatedEntity) {
        return null;
    }

    @Override
    protected AbstractEntityCreatedEvent<Ssg4mSyntheticPathway> newCreationEvent(Ssg4mSyntheticPathway createdEntity) {
        return null;
    }

    @Override
    public Long getIdFrom(Ssg4mSyntheticPathway entity) {
        return entity.getId();
    }

    @Override
    protected List<Ssg4mSyntheticPathway> fromNewJsonList(JsonNode list) throws IOException {
        return null;
    }

    /*
    @Override
    protected Ssg4mSyntheticPathway fixUpdatedIfNeeded(Ssg4mSyntheticPathway oldEntity, Ssg4mSyntheticPathway updatedEntity) {
        //force the "owner" on all the updated fields to point to the old version so the uuids are correct
        return updatedEntity;
    }
    */

    @Override
    protected Ssg4mSyntheticPathway fromUpdatedJson(JsonNode json) throws IOException {
        //TODO should we make any edits to remove fields?
        return objectMapper.convertValue(json, Ssg4mSyntheticPathway.class);
    }

    @Override
    protected List<Ssg4mSyntheticPathway> fromUpdatedJsonList(JsonNode list) throws IOException {
        return null;
    }

    @Override
    protected JsonNode toJson(Ssg4mSyntheticPathway ssg4mSyntheticPathway) throws IOException {
        return objectMapper.valueToTree(ssg4mSyntheticPathway);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Optional<Ssg4mSyntheticPathway> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Ssg4mSyntheticPathway> flexLookup(String someKindOfId) {
        if (someKindOfId == null){
            return Optional.empty();
        }
        return repository.findById(Long.parseLong(someKindOfId));
    }

    @Override
    protected Optional<Long> flexLookupIdOnly(String someKindOfId) {
        //easiest way to avoid deduping data is to just do a full flex lookup and then return id
        Optional<Ssg4mSyntheticPathway> found = flexLookup(someKindOfId);
        if(found.isPresent()){
            return Optional.of(found.get().synthPathwaySkey);
        }
        return Optional.empty();
    }

}
