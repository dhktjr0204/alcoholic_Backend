package graduation.alcoholic.domain;

import graduation.alcoholic.domain.Alcohol;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class Visit {

    @Id
   // @JoinColumn(name="a_id",referencedColumnName="id")
    private Long a_id;

    @OneToOne
    @MapsId
    @JoinColumn(name="a_id")
    private Alcohol alcohol;

    private Long female;

    private Long male;

    private Long twentys;

    private Long thirtys;

    private Long fourtys;

    private Long fiftys;

    @Builder
    public Visit (Alcohol alcohol, Long female, Long male, Long twentys, Long thirtys, Long fourtys, Long fiftys) {
        this.alcohol=alcohol;
        this.female=female;
        this.male=male;
        this.twentys=twentys;
        this.thirtys=thirtys;
        this.fourtys=fourtys;
        this.fiftys=fiftys;
    }

    public void updateFemale() {
        this.female++;
    }

    public void updateMale() {
        this.male++;
    }
    public void updateTwentys() {
        this.twentys++;
    }
    public void updateThirtys() {
        this.thirtys++;
    }
    public void updateFourtys() {
        this.fourtys++;
    }
    public void updateFiftys() {
        this.fiftys++;
    }

}
