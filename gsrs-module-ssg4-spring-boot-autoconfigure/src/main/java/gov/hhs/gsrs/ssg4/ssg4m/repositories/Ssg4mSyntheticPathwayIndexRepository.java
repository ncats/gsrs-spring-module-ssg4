package gov.hhs.gsrs.ssg4.ssg4m.repositories;

import gov.hhs.gsrs.ssg4.ssg4m.models.*;

//import gsrs.repository.GsrsVersionedRepository;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Service
//@Repository
public interface Ssg4mSyntheticPathwayIndexRepository extends JpaRepository<Ssg4mSyntheticPathwayDetail, Long> { //GsrsVersionedRepository<Ssg4mSyntheticPathway, Long> {

    Optional<Ssg4mSyntheticPathwayDetail> findById(Long Ssg4mSyntheticPathwayDetail);

    List<Ssg4mSyntheticPathwayDetail> findBySynthPathwaySkey(Long parentSkey);

}
