package graduation.alcoholic.collection.collectionInfo;

import graduation.alcoholic.domain.CollectionInfo;
import graduation.alcoholic.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
