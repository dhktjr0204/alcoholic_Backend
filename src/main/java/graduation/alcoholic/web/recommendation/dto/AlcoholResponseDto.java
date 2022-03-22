package graduation.alcoholic.web.recommendation.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Data;


@Data
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

    private Taste taste1;
    private Taste taste2;
    private Taste taste3;
    private Taste taste4;
    private Taste taste5;

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
        this.taste1 = entity.getTaste1();
        this.taste2 = entity.getTaste2();
        this.taste3 = entity.getTaste3();
        this.taste4 = entity.getTaste4();
        this.taste5 = entity.getTaste5();
    }
}
