package graduation.alcoholic.collection.collectionInfo;

import graduation.alcoholic.domain.CollectionInfo;
import graduation.alcoholic.domain.User;

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
