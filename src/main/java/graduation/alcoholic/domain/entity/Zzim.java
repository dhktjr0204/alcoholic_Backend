package graduation.alcoholic.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
    public Zzim(User user, Alcohol alcohol) {
        this.user = user;
        this.alcohol = alcohol;
        this.id = new ZzimId(user.getId(), alcohol.getId());
    }
}
