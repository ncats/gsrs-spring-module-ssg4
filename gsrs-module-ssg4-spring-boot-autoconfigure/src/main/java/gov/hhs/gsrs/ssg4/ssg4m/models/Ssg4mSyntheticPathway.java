package gov.hhs.gsrs.ssg4.ssg4m.models;

/*
import gsrs.BackupEntityProcessorListener;
import gsrs.GsrsEntityProcessorListener;
import gsrs.indexer.IndexerEntityListener;
import ix.core.models.Backup;
import ix.core.models.Indexable;
import ix.core.models.IndexableRoot;
*/

import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

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

@Data
@Entity
@Table(name="GSRS_SYNTH_PTWY")
public class Ssg4mSyntheticPathway extends Ssg4mCommanData {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "SYNTH_PTWY_SKEY")
    public Long synthPathwaySkey;

    //@GenericGenerator(name = "NullUUIDGenerator", strategy = "ix.ginas.models.generators.NullUUIDGenerator")
    //@GeneratedValue(generator = "NullUUIDGenerator")
    //maintain backwards compatibility with old GSRS store it as varchar(40) by default hibernate will store uuids as binary
    @Type(type = "uuid-char" )
    //@Column(length =40, updatable = false, unique = true)
    //public UUID uuid;
    @Column(name = "SYNTH_PTWY_ID")
    public UUID synthPathwayId;

    @Version
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

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "SBMSN_DATA_TXT")
    public String sbmsnDataText;

    @JsonIgnore
    public JsonNode getSbmsnDataJson() {
        JsonNode jsonNode = null;
        try {
            if (sbmsnDataText != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                jsonNode = objectMapper.readTree(sbmsnDataText);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return jsonNode;
    }

    public String getSbmsnDataText() {
        return this.sbmsnDataText;
    }

    public void setSbmsnDataText(String text) {
        this.sbmsnDataText = text;
    }

    @Column(name = "PRNT_SBSTNC_UUID")
    public String printSbstncUuid;

    @Column(name = "PRNT_SBSTNC_PRFRD_NM")
    public String printSbstncPrfrdNm;

    @Column(name = "SBMSN_IMG")
    public Blob sbmsnImage;

    /*
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
    */
    public Long getId() {
        return synthPathwaySkey;
    }

}
