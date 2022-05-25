package graduation.alcoholic.web.collection.collectionContent.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionInfo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class CollectionContentSaveRequestDto {

    private CollectionInfo collection;

    private List<Alcohol> alcoholList;

    @Builder
    public CollectionContentSaveRequestDto(CollectionInfo collection, List<Alcohol> alcoholList) {
        this.collection = collection;
        this.alcoholList = alcoholList;
    }

    public List<CollectionContent> toEntity() {

        List<CollectionContent> collectionContentList = new ArrayList<>();
        for (int i =0; i< alcoholList.size(); i++) {
            collectionContentList.add(CollectionContent.builder()
                    .collection(collection)
                    .alcohol(alcoholList.get(i))
                    .build());
        }
        return collectionContentList;
    }
}
