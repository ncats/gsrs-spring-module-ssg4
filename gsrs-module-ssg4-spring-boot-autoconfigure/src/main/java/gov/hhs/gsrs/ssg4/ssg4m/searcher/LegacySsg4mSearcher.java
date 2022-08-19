package gov.hhs.gsrs.ssg4.ssg4m.searcher;

import gov.hhs.gsrs.ssg4.ssg4m.models.Ssg4mSyntheticPathway;
import gov.hhs.gsrs.ssg4.ssg4m.repositories.*;

import gsrs.legacy.LegacyGsrsSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegacySsg4mSearcher extends LegacyGsrsSearchService<Ssg4mSyntheticPathway> {

    @Autowired
    public LegacySsg4mSearcher(Ssg4mRepository repository) {
        super(Ssg4mSyntheticPathway.class, repository);
    }
}
