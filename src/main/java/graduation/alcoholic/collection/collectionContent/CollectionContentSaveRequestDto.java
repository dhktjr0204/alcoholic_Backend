package graduation.alcoholic.collection.collectionContent;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.CollectionContent;
import graduation.alcoholic.domain.CollectionInfo;
import lombok.Builder;


public class CollectionContentSaveRequestDto {

    private CollectionInfo collection;

    private Alcohol alcohol;

    @Builder
    public CollectionContentSaveRequestDto(CollectionInfo collection, Alcohol alcohol) {
        this.collection = collection;
        this.alcohol = alcohol;
    }

    public CollectionContent toEntity() {
        return CollectionContent.builder()
                .collection(collection)
                .alcohol(alcohol)
                .build();
    }
}
