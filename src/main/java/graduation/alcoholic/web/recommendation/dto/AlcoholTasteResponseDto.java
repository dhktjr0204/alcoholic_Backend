package graduation.alcoholic.web.recommendation.dto;
import graduation.alcoholic.domain.entity.AlcoholTaste;
import graduation.alcoholic.domain.enums.Taste;
import lombok.Getter;


@Getter
public class AlcoholTasteResponseDto {

    private Long id;

    private Taste taste1;
    private Taste taste2;
    private Taste taste3;
    private Taste taste4;
    private Taste taste5;


    public AlcoholTasteResponseDto(AlcoholTaste entity) {
        this.id = entity.getId();
        this.taste1 = entity.getTaste1();
        this.taste2 = entity.getTaste2();
        this.taste3 = entity.getTaste3();
        this.taste4 = entity.getTaste4();
        this.taste5 = entity.getTaste5();
    }
}
