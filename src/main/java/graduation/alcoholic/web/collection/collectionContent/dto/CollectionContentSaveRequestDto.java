package graduation.alcoholic.web.collection.collectionContent.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionInfo;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;


public class CollectionContentSaveRequestDto {

    private CollectionInfo collection;

    private List<Alcohol> alcohol;

    @Builder
    public CollectionContentSaveRequestDto(CollectionInfo collection, List<Alcohol> alcohol) {
        this.collection = collection;
        this.alcohol = alcohol;
    }

    public List<CollectionContent> toEntity() {

        List<CollectionContent> collectionContentList = new ArrayList<>();
        CollectionContent collectionContent;
        for (int i=0 ; i < alcohol.size(); i++) {
            collectionContent = CollectionContent.builder()
                    .collection(collection)
                    .alcohol(alcohol.get(i))
                    .build();
            collectionContentList.add(collectionContent);
        }
        return collectionContentList;

    }
}
