package graduation.alcoholic.web.collection.collectionInfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CollectionInfoUpdateRequestDto {

    private String title;

    private String description;

    @Builder
    public CollectionInfoUpdateRequestDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
