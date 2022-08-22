package gov.hhs.gsrs.ssg4.ssg4m.processors;

import gov.hhs.gsrs.ssg4.ssg4m.controllers.Ssg4mController;
import gov.hhs.gsrs.ssg4.ssg4m.models.*;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Ssg4mProcessor {

    @Autowired
    public Ssg4mController ssg4mController;

    public Class<Ssg4mSyntheticPathway> getEntityClass() {
        return Ssg4mSyntheticPathway.class;
    }

    public void prePersist(final Ssg4mSyntheticPathway obj) {
    }

    public void preUpdate(Ssg4mSyntheticPathway obj) {
    }

    public void preRemove(Ssg4mSyntheticPathway obj) {
    }

    public void postLoad(Ssg4mSyntheticPathway obj) throws IOException {
    }

}

