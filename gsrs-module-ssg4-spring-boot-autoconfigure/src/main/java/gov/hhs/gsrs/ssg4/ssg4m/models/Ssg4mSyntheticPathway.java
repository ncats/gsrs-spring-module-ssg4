package gov.hhs.gsrs.ssg4.ssg4m.models;

import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
import java.nio.charset.StandardCharsets;

@Data
@Entity
@Table(name = "GSRS_SYNTH_PTWY")
public class Ssg4mSyntheticPathway {

    @Id
    @SequenceGenerator(name="synthSeq", sequenceName="GSRS_SYNTH_PTWY_SQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "synthSeq")
    @Column(name = "SYNTH_PTWY_SKEY")
    public Long synthPathwaySkey;

    //maintain backwards compatibility with old GSRS store it as varchar(40) by default hibernate will store uuids as binary
    @Type(type = "uuid-char")
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
        } catch (Exception ex) {
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

    /* USING CLOB */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "SBMSN_IMG")
    public String sbmsnImage;

    public String getSbmsnImage() {
        return this.sbmsnImage;
    }

    public void setSbmsnImage(String image) {
        this.sbmsnImage = image;
    }

    /* USING BLOB */
    /*
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "SBMSN_IMG")
    public byte[] sbmsnImage;

    public String getSbmsnImage() {
        if (this.sbmsnImage == null) {
            return null;
        }
        return new String(this.sbmsnImage, StandardCharsets.UTF_8);
    }

    public void setSbmsnImage(String text) {
        this.sbmsnImage = text.getBytes(StandardCharsets.UTF_8);
    }
    */

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

    public Long getId() {
        return synthPathwaySkey;
    }

}
