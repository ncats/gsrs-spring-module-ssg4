package gov.hhs.gsrs.ssg4.ssg4m;

import gov.hhs.gsrs.ssg4.ssg4m.controllers.Ssg4mController;
import gov.hhs.gsrs.ssg4.ssg4m.searcher.LegacySsg4mSearcher;
import gov.hhs.gsrs.ssg4.ssg4m.services.Ssg4mEntityService;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class Ssg4mSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                Ssg4mEntityService.class.getName(),
                LegacySsg4mSearcher.class.getName(),
                Ssg4mController.class.getName()
        };
    }
}
