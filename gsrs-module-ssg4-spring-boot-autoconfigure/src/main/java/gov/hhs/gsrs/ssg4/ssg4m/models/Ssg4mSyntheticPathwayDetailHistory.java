package gov.hhs.gsrs.ssg4.ssg4m.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

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
