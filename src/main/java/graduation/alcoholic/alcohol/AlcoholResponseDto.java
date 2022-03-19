package graduation.alcoholic.alcohol;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class AlcoholResponseDto {
    private Long id;
    private String name;
    private String image;
    private Integer price;
    private Integer review;

    @Builder
    public AlcoholResponseDto(Alcohol a) {
        this.id=a.getId();
        this.name=a.getName();
        this.image=a.getImage();
        this.price=a.getPrice();
        this.review=a.getReviews().size();

    }
}
