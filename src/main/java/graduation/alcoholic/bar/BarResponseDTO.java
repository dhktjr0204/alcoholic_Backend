package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class BarResponseDTO {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String location;
    private String image;
    private LocalDateTime modified_date;


    public BarResponseDTO(Bar entity) {
        this.id = entity.getId();
        this.nickname = entity.getUser().getNickname();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.location = entity.getLocation();
        this.image = entity.getImage();
        this.modified_date = entity.getModifiedDate();
    }

    public BarResponseDTO(Long id, String nickname, String title, String content, String location, String image, LocalDateTime modified_date) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.location = location;
        this.image = image;
        this.modified_date = modified_date;
    }
}
