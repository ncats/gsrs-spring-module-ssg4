package gov.hhs.gsrs.ssg4.ssg4m.models;

import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.IndexableRoot;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Clob;
import java.sql.Blob;

@IndexableRoot
@Backup
@Data
@Entity
@Table(name="GSRS_SYNTH_PTWY")
public class Ssg4mSyntheticPathway extends Ssg4mCommanData {

    static final ThreadLocal<DateFormat> YEAR_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }
    };
    //Today's Date
    static final LocalDate todayDate = LocalDate.now();

    @Id
    @Column(name = "SYNTH_PTWY_SKEY")
    public Long synthPathwaySkey;

    @Column(name = "SYNTH_PTWY_ID")
    public String synthPathwayId;

    @Column(name = "VRSN_NUM")
    public Long versionNumber;

    @Column(name = "APP_CNTR_CD")
    public String appCenterCd;

    @Column(name = "APP_TYPE")
    public String appType;

    @Column(name = "APP_NUM")
    public Long appNumber;

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

    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    public List<Ssg4mSyntheticPathwayDetail> ssg4mSyntheticPathwayDetailsList = new ArrayList<>();

    public void setSsg4mSyntheticPathwayDetailsList(List<Ssg4mSyntheticPathwayDetail> ssg4mSyntheticPathwayDetailsList) {
        this.ssg4mSyntheticPathwayDetailsList = ssg4mSyntheticPathwayDetailsList;
        if (ssg4mSyntheticPathwayDetailsList != null) {
            for (Ssg4mSyntheticPathwayDetail ssg4mSynthDetail : ssg4mSyntheticPathwayDetailsList) {
                ssg4mSynthDetail.setOwner(this);
            }
        }
    }

    public Long getId() {
        return synthPathwaySkey;
    }

}
