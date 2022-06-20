package graduation.alcoholic.web.collection.collectionInfo.dto;

import graduation.alcoholic.domain.entity.CollectionInfo;
import graduation.alcoholic.domain.entity.User;
import lombok.Data;

@Data
public class CollectionInfoResponseDto {

    private Long id;

    private User user;

    private String title;

    private String description;

    public CollectionInfoResponseDto(CollectionInfo entity) {
        this.id = entity.getId();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
    }
}
