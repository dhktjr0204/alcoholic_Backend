package graduation.alcoholic.web.main;

import graduation.alcoholic.domain.entity.CollectionInfo;
import graduation.alcoholic.web.collection.collectionContent.dto.CollectionContentResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class MainResponseDto {
    long collectionId ;
    String username;
    String title;
    String description;

    List<CollectionContentResponseDto> content;

    MainResponseDto (CollectionInfo info, List<CollectionContentResponseDto> content) {
        this. collectionId= info.getId();
        this.title = info.getTitle();
        this.description = info.getDescription();
        this.username = info.getUser().getName();
        this.content = content;
    }

}
