package gov.hhs.gsrs.ssg4.ssg4m.models;

import ix.core.SingleParent;
import ix.core.models.Indexable;
import ix.core.models.ParentReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SingleParent
@Data
@Entity
@Table(name="GSRS_SYNTH_PTWY_DTL")
public class Ssg4mSyntheticPathwayDetail extends Ssg4mCommanData {

    @Id
    @Column(name = "SYNTH_PTWY_DTL_SKEY")
    public Long synthPathwayDetailSkey;

    //@Column(name="SYNTH_PTWY_SKEY")
    //public String synthPathwaySkey;

    @Column(name="SBSTNC_UUID")
    public String sbstncUuid;

    @Column(name="SBSTNC_PFRD_NM")
    public String sbstncPfrdNm;

    @Column(name="SBSTNC_REACTN_SECT_NM")
    public String sbstncReactnSectNm;

    @Column(name="SBSTNC_ROLE_NM")
    public String sbstncRoleNm;

    @Indexable(indexed=false)
    @ParentReference
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="SYNTH_PTWY_SKEY",referencedColumnName="SYNTH_PTWY_SKEY")
    public Ssg4mSyntheticPathway owner;

    public void setOwner(Ssg4mSyntheticPathway synthPathway) {
        this.owner = synthPathway;
    }
}
