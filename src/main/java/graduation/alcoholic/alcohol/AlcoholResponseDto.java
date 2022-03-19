package graduation.alcoholic.alcohol;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import lombok.Builder;
import lombok.Data;

@Data
public class AlcoholResponseDto {
    private Long id;
    private String name;
    private String image;
    private Integer price;
    private Review review;

    @Builder
    public AlcoholResponseDto(Alcohol a) {
        this.id=a.getId();
        this.name=a.getName();
        this.image=a.getImage();
        this.price=a.getPrice();

    }
}
