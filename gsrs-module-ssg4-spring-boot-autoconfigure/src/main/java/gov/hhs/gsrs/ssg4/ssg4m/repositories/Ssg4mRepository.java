package gov.hhs.gsrs.ssg4.ssg4m.repositories;

import gov.hhs.gsrs.ssg4.ssg4m.models.*;

import gsrs.repository.GsrsVersionedRepository;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface Ssg4mRepository extends GsrsVersionedRepository<Ssg4mSyntheticPathway, Long> {

    Optional<Ssg4mSyntheticPathway> findById(Long id);

}
