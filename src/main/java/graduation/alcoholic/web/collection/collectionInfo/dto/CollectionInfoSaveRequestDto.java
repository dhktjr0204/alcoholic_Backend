package graduation.alcoholic.web.collection.collectionInfo.dto;

import graduation.alcoholic.domain.entity.CollectionInfo;
import graduation.alcoholic.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollectionInfoSaveRequestDto {

    private User user;

    private String title;

    private String description;

    @Builder
    public CollectionInfoSaveRequestDto(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }

    public void setUser (User user){
        this.user = user;
    }

    public CollectionInfo toEntity() {
        return CollectionInfo.builder()
                .user(user)
                .title(title)
                .description(description)
                .build();

    }
}
