package graduation.alcoholic.review.domain.dtos;

import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {

    private String content;
    private String image;

    private Integer star;

    private Taste taste1;
    private Taste taste2;
    private Taste taste3;
    private Taste taste4;
    private Taste taste5;

    @Builder
    public ReviewUpdateRequestDto(String content, String image, Integer star,
                                  Taste taste1, Taste taste2, Taste taste3, Taste taste4, Taste taste5) {
        this.content = content;
        this.image = image;
        this.star = star;
        this.taste1 = taste1;
        this.taste2 = taste2;
        this.taste3 = taste3;
        this.taste4 = taste4;
        this.taste5 = taste5;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
