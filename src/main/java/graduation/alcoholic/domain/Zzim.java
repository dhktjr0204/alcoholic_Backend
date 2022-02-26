package graduation.alcoholic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
public class Zzim{

    @MapsId("user_id")
    @ManyToOne
    private User user;

    @MapsId("alcohol_id")
    @ManyToOne
    private Alcohol alcohol;

    @EmbeddedId
    private ZzimId id;


    @Builder
    public Zzim(User user, Alcohol alcohol, ZzimId id) {
        this.user = user;
        this.alcohol = alcohol;
        this.id = new ZzimId();
    }
}
