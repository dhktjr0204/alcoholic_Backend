package graduation.alcoholic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class ZzimId implements Serializable {

    private Long user_id;
    private Long alcohol_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZzimId zzimId = (ZzimId) o;
        return user_id.equals(zzimId.user_id) && alcohol_id.equals(zzimId.alcohol_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, alcohol_id);
    }

    public ZzimId (Long user_id, Long alcohol_id) {
        this.user_id=user_id;
        this.alcohol_id=alcohol_id;
    }
}
