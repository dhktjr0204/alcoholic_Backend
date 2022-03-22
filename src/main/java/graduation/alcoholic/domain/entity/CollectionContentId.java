package graduation.alcoholic.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class CollectionContentId implements Serializable {

    public Long collection_id;

    public Long alcohol_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionContentId that = (CollectionContentId) o;
        return collection_id.equals(that.collection_id) && alcohol_id.equals(that.alcohol_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection_id, alcohol_id);
    }
}
