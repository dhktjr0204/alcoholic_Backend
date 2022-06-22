package graduation.alcoholic.web.collection.collectionContent.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionContentId;
import graduation.alcoholic.domain.entity.CollectionInfo;
import lombok.Data;

import java.util.List;

@Data
public class CollectionContentResponseDto {

    private Long id;

    private String image;

    private String name;

    public CollectionContentResponseDto(CollectionContent entity) {
        this.id = entity.getAlcohol().getId();
        this.image=entity.getAlcohol().getImage();
        this.name = entity.getAlcohol().getName();
    }
}
