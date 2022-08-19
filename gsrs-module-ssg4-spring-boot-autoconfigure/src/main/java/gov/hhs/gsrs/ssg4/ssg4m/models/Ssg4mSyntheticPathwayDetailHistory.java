package gov.hhs.gsrs.ssg4.ssg4m.models;

import gsrs.model.AbstractGsrsEntity;
import gsrs.security.GsrsSecurityUtils;
import ix.core.SingleParent;
import ix.core.models.Indexable;
import ix.core.models.ParentReference;
import ix.core.models.Principal;
import ix.core.models.UserProfile;
import ix.ginas.models.serialization.GsrsDateDeserializer;
import ix.ginas.models.serialization.GsrsDateSerializer;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@SingleParent
@Data
@Entity
@Table(name="GSRS_SYNTH_PTWY_DTL_HIST")
public class Ssg4mSyntheticPathwayDetailHistory extends Ssg4mCommanData {

    @Id
    @Column(name = "HIST_SKEY")
    public Long synthPathwayDetailHistorySkey;

    @Column(name = "SYNTH_PTWY_DTL_SKEY")
    public Long synthPathwayDetailSkey;

    @Column(name="SYNTH_PTWY_SKEY")
    public String synthPathwaySkey;

    @Column(name="SBSTNC_UUID")
    public String sbstncUuid;

    @Column(name="SBSTNC_PFRD_NM")
    public String sbstncPfrdNm;

    @Column(name="SBSTNC_REACTN_SECT_NM")
    public String sbstncReactnSectNm;

    @Column(name="SBSTNC_ROLE_NM")
    public String sbstncRoleNm;

    @Column(name = "ACTN_BY")
    public String actnBy;

    @Column(name = "ACTN_DESC")
    public String actnDescription;

    @Column(name = "ACTN_DTTM")
    public Date actnDate;

}
