package gov.hhs.gsrs.ssg4.ssg4m.processors;

import gov.hhs.gsrs.ssg4.ssg4m.controllers.Ssg4mController;
import gov.hhs.gsrs.ssg4.ssg4m.models.*;

import ix.core.EntityProcessor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class Ssg4mProcessor implements EntityProcessor<Ssg4mSyntheticPathway> {

    @Autowired
    public Ssg4mController ssg4mController;

    @Override
    public Class<Ssg4mSyntheticPathway> getEntityClass() {
        return Ssg4mSyntheticPathway.class;
    }

    @Override
    public void prePersist(final Ssg4mSyntheticPathway obj) {
    }

    @Override
    public void preUpdate(Ssg4mSyntheticPathway obj) {
    }

    @Override
    public void preRemove(Ssg4mSyntheticPathway obj) {
    }

    @Override
    public void postLoad(Ssg4mSyntheticPathway obj) throws FailProcessingException {
    }

}

