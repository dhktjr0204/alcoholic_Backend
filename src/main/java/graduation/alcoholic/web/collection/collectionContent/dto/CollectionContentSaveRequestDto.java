package graduation.alcoholic.web.collection.collectionContent.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionInfo;
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
