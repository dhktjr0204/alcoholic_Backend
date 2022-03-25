package graduation.alcoholic.web.bar.dto;

import graduation.alcoholic.domain.entity.Bar;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class BarResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String location;
    private String image;
    private LocalDateTime modified_date;


    public BarResponseDto(Bar entity) {
        this.id = entity.getId();
        this.nickname = entity.getUser().getNickname();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.location = entity.getLocation();
        this.image = entity.getImage();
        this.modified_date = entity.getModifiedDate();
    }

}
