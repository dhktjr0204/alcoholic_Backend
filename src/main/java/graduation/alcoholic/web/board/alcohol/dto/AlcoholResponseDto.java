package graduation.alcoholic.web.board.alcohol.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import lombok.Builder;
import lombok.Data;

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
