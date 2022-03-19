package graduation.alcoholic.alcohol;

import graduation.alcoholic.domain.Alcohol;
import lombok.Builder;
import lombok.Data;

@Data
public class AlcoholResponseDto {
    private Long id;
    private String name;
    private String image;

    @Builder
    public AlcoholResponseDto(Alcohol a) {
        this.id=a.getId();
        this.name=a.getName();
        this.image=a.getImage();
    }
}
