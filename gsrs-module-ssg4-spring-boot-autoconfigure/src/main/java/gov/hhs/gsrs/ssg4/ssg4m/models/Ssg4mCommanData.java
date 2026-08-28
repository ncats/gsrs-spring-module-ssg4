package gov.hhs.gsrs.ssg4.ssg4m.models;
/*
import gsrs.model.AbstractGsrsEntity;
import gsrs.security.GsrsSecurityUtils;
import gsrs.ForceUpdateDirtyMakerMixin;

import ix.core.models.Indexable;
import ix.core.models.Principal;
import ix.core.models.UserProfile;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;
*/

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedSuperclass
public class Ssg4mCommanData {

    @Column(name = "AUD_INS_USR_ID")
    private String createdBy;

    @Column(name = "AUD_UPD_USR_ID")
    private String modifiedBy;

    @CreatedDate
    @Column(name = "AUD_INS_DTTM")
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "AUD_UPD_DTTM")
    private Date lastModifiedDate;

    public Ssg4mCommanData () {
    }

}
