package graduation.alcoholic.recommendation;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Getter;


@Getter
public class AlcoholResponseDto {

    private Long id;

    private Type type;

    private String name;

    private Double degree;

    private Integer capacity;

    private Integer price;

    private String image;

    private String manufacturer;

    private String content;

    private Taste taste_1;
    private Taste taste_2;
    private Taste taste_3;
    private Taste taste_4;
    private Taste taste_5;

    public AlcoholResponseDto(Alcohol entity) {
        this.id = entity.getId();
        this.type = entity.getType();
        this.name = entity.getName();
        this.degree = entity.getDegree();
        this.capacity = entity.getCapacity();
        this.price = entity.getPrice();
        this.image = entity.getImage();
        this.manufacturer = entity.getManufacturer();
        this.content = entity.getContent();
        this.taste_1 = entity.getTaste_1();
        this.taste_2 = entity.getTaste_2();
        this.taste_3 = entity.getTaste_3();
        this.taste_4 = entity.getTaste_4();
        this.taste_5 = entity.getTaste_5();
    }
}
