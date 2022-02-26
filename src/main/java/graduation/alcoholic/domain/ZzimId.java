package graduation.alcoholic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
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
}
