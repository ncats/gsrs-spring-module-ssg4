package gov.hhs.gsrs.ssg4.ssg4m.models;

import ix.core.SingleParent;
import ix.core.models.Indexable;
import ix.core.models.ParentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Clob;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.*;

@SingleParent
@Data
@Entity
@Table(name="GSRS_SYNTH_PTWY_HIST")
public class Ssg4mSyntheticPathwayHistory extends Ssg4mCommanData {

    @Id
    @Column(name = "HIST_SKEY")
    public long historySkey;

    @Column(name = "SYNTH_PTWY_SKEY")
    public long synthPathwaySkey;

    @Column(name = "SYNTH_PTWY_ID")
    public String synthPathwayId;

    @Column(name = "VRSN_NUM")
    public long versionNumber;

    @Column(name = "APP_CNTR_CD")
    public String appCenterCd;

    @Column(name = "APP_TYPE")
    public String appType;

    @Column(name = "APP_NUM")
    public long appNumber;

    @Column(name = "VRSN_TYPE")
    public String versionType;

    @Column(name = "VRSN_STUS_CD")
    public String versionStatusCd;

    @Column(name = "SBMSN_DATA_TXT")
    public Clob sbmsnDataText;

    @Column(name = "PRNT_SBSTNC_UUID")
    public String printSbstncUuid;

    @Column(name = "PRNT_SBSTNC_PRFRD_NM")
    public String printSbstncPrfrdNm;

    @Column(name = "SBMSN_IMG")
    public Blob sbmsnImage;

    @Column(name = "ACTN_BY")
    public String actnBy;

    @Column(name = "ACTN_DESC")
    public String actnDescription;

    @Column(name = "ACTN_DTTM")
    public Date actnDate;

}
