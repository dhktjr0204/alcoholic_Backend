package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.enums.Taste;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class BarResponseDTO {
    private Long id;
    private Long user_id;
    private String title;
    private String content;
    private String location;
    private String image;
    private LocalDateTime modified_date;


    public BarResponseDTO(Bar entity) {
        this.id = entity.getId();
        this.user_id = entity.getUser().getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.location = entity.getLocation();
        this.image = entity.getImage();
        this.modified_date = entity.getModifiedDate();
    }
}
