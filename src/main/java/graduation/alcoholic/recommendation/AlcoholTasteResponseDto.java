package graduation.alcoholic.recommendation;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Taste;
import lombok.Getter;

@Getter
public class AlcoholTasteResponseDto {

    private Long id;

    private Taste taste_1;
    private Taste taste_2;
    private Taste taste_3;
    private Taste taste_4;
    private Taste taste_5;



    public AlcoholTasteResponseDto(AlcoholTaste entity) {
        this.id = entity.getId();
        this.taste_1 = entity.getTaste_1();
        this.taste_2 = entity.getTaste_2();
        this.taste_3 = entity.getTaste_3();
        this.taste_4 = entity.getTaste_4();
        this.taste_5 = entity.getTaste_5();
    }
}
