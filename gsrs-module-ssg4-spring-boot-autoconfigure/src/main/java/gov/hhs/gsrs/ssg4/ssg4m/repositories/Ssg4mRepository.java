package gov.hhs.gsrs.ssg4.ssg4m.repositories;

import gov.hhs.gsrs.ssg4.ssg4m.models.*;

import gov.hhs.gsrs.ssg4.Util;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface Ssg4mRepository extends JpaRepository<Ssg4mSyntheticPathway, Long> {

    Optional<Ssg4mSyntheticPathway> findById(Long synthPathwaySkey);

    Optional<Ssg4mSyntheticPathway> findBySynthPathwayId(UUID synthPathwayId);

    @Query("SELECT a from Ssg4mSyntheticPathwayDetail a WHERE a.sbstncUuid = ?1 order by a.synthPathwaySkey")
    List<Ssg4mSyntheticPathwayDetail> findBySyntheticDetailBySubUuid(String sbstncUuid);
}
