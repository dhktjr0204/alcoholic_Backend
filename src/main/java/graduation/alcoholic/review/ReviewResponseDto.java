package graduation.alcoholic.review;

import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {

    private Long id;

    private Long user_id;
    private Long alcohol_id;

    private String content;
    private String image;

    private Integer star;

    private Taste taste_1;
    private Taste taste_2;
    private Taste taste_3;
    private Taste taste_4;
    private Taste taste_5;

    private LocalDateTime modified_date;


    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.user_id = entity.getUser().getId();
        this.alcohol_id = entity.getAlcohol().getId();
        this.content = entity.getContent();
        this.image = entity.getImage();
        this.star = entity.getStar();
        this.taste_1 = entity.getTaste_1();
        this.taste_2 = entity.getTaste_2();
        this.taste_3 = entity.getTaste_3();
        this.taste_4 = entity.getTaste_4();
        this.taste_5 = entity.getTaste_5();
        this.modified_date = entity.getModifiedDate();
    }

    @Builder
    public ReviewResponseDto(Taste taste_1, Taste taste_2, Taste taste_3, Taste taste_4, Taste taste_5) {
        this.taste_1 = taste_1;
        this.taste_2 = taste_2;
        this.taste_3 = taste_3;
        this.taste_4 = taste_4;
        this.taste_5 = taste_5;
    }
}
