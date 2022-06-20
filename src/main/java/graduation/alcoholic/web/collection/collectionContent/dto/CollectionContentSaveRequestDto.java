package graduation.alcoholic.web.collection.collectionContent.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionInfo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CollectionContentSaveRequestDto {

    private CollectionInfo collection;
    private List<Alcohol> alcoholList;

    @Builder
    public CollectionContentSaveRequestDto(List<Alcohol> alcoholList) {
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
