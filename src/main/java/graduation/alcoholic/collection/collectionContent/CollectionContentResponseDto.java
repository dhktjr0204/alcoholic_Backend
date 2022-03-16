package graduation.alcoholic.collection.collectionContent;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.CollectionContent;
import graduation.alcoholic.domain.CollectionContentId;
import graduation.alcoholic.domain.CollectionInfo;

public class CollectionContentResponseDto {

    private CollectionContentId collectionContentId;

    private CollectionInfo collection;

    private Alcohol alcohol;

    public CollectionContentResponseDto(CollectionContent entity) {
        this.collection = entity.getCollection();
        this.alcohol = entity.getAlcohol();
    }
}
