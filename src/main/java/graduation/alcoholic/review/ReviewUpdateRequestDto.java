package graduation.alcoholic.review;


import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    private String content;
    private String image;

    private Integer star;

    private Taste taste_1;
    private Taste taste_2;
    private Taste taste_3;
    private Taste taste_4;
    private Taste taste_5;

    @Builder

    public ReviewUpdateRequestDto(String content, String image, Integer star,
                                  Taste taste_1, Taste taste_2, Taste taste_3, Taste taste_4, Taste taste_5) {
        this.content = content;
        this.image = image;
        this.star = star;
        this.taste_1 = taste_1;
        this.taste_2 = taste_2;
        this.taste_3 = taste_3;
        this.taste_4 = taste_4;
        this.taste_5 = taste_5;
    }
}
