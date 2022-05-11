package graduation.alcoholic.web.board.alcohol.dto;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.Visit;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Builder;
import lombok.Data;

@Data
public class AlcoholDetailResponseDto {

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

    private Visit visit;


    @Builder
    public AlcoholDetailResponseDto(Alcohol a) {
        this.id=a.getId();
        this.name=a.getName();
        this.type=a.getType();
        this.degree=a.getDegree();
        this.capacity=a.getCapacity();
        this.manufacturer=a.getManufacturer();
        this.price=a.getPrice();
        this.image=a.getImage();
        this.content=a.getContent();
        this.taste_1=a.getTaste1();
        this.taste_2=a.getTaste2();
        this.taste_3=a.getTaste3();
        this.taste_4=a.getTaste4();
        this.taste_5=a.getTaste5();
    }
}
