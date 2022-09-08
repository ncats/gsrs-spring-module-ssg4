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
public interface Ssg4mRepository extends JpaRepository<Ssg4mSyntheticPathway, Long> { //GsrsVersionedRepository<Ssg4mSyntheticPathway, Long> {

    Optional<Ssg4mSyntheticPathway> findById(Long synthPathwaySkey);

    Optional<Ssg4mSyntheticPathway> findBySynthPathwayId(String synthPathwayId);

}
