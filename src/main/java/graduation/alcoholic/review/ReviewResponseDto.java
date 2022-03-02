package graduation.alcoholic.review;

import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.enums.Taste;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long id;

    private User user;
    private Alcohol alcohol;

    private String content;
    private String image;

    private Integer star;

    private Taste taste_1;
    private Taste taste_2;
    private Taste taste_3;
    private Taste taste_4;
    private Taste taste_5;


    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.user = entity.getUser();
        this.alcohol = entity.getAlcohol();
        this.content = entity.getContent();
        this.image = entity.getImage();
        this.star = entity.getStar();
        this.taste_1 = entity.getTaste_1();
        this.taste_2 = entity.getTaste_2();
        this.taste_3 = entity.getTaste_3();
        this.taste_4 = entity.getTaste_4();
        this.taste_5 = entity.getTaste_5();
    }
}
