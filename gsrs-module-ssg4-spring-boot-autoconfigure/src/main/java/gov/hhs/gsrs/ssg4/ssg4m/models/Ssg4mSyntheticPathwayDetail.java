package gov.hhs.gsrs.ssg4.ssg4m.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="GSRS_SYNTH_PTWY_DTL")
public class Ssg4mSyntheticPathwayDetail { //extends Ssg4mCommanData {

    @Id
    @SequenceGenerator(name="detSeq", sequenceName="GSRS_SYNTH_PTWY_DTL_SQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "detSeq")
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "SYNTH_PTWY_DTL_SKEY")
    public Long synthPathwayDetailSkey;

    @Column(name = "SYNTH_PTWY_SKEY")
    public Long synthPathwaySkey;

    @Column(name="SBSTNC_UUID")
    public String sbstncUuid;

    @Column(name="SBSTNC_PFRD_NM")
    public String sbstncPfrdNm;

    @Column(name="SBSTNC_REACTN_SECT_NM")
    public String sbstncReactnSectNm;

    @Column(name="SBSTNC_ROLE_NM")
    public String sbstncRoleNm;

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

    /*
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="SYNTH_PTWY_SKEY",referencedColumnName="SYNTH_PTWY_SKEY")
    public Ssg4mSyntheticPathway owner;

    public void setOwner(Ssg4mSyntheticPathway synthPathway) {
        this.owner = synthPathway;
    }
     */
}
