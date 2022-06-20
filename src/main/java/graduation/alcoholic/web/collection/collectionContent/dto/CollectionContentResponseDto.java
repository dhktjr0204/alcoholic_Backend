package graduation.alcoholic.web.collection.collectionContent.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionContentId;
import graduation.alcoholic.domain.entity.CollectionInfo;
import lombok.Data;

@Data
public class CollectionContentResponseDto {

    private CollectionContentId collectionContentId;

    private CollectionInfo collection;

    private Alcohol alcohol;

    public CollectionContentResponseDto(CollectionContent entity) {
        this.collection = entity.getCollection();
        this.alcohol = entity.getAlcohol();
    }
}
