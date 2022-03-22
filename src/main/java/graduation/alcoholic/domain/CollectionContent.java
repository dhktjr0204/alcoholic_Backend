package graduation.alcoholic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CollectionContent {

    @MapsId("collection_id")
    @ManyToOne
    private CollectionInfo collection;

    @MapsId("alcohol_id")
    @ManyToOne
    private Alcohol alcohol;

    @EmbeddedId
    private CollectionContentId id;

    @Builder
    public CollectionContent(CollectionInfo collection, Alcohol alcohol) {
        this.collection = collection;
        this.alcohol = alcohol;
        this.id = new CollectionContentId();
    }
}
