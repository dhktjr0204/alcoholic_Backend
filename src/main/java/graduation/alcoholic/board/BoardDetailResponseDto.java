package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Visit;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardDetailResponseDto {
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
    public BoardDetailResponseDto(Alcohol a) {
        this.id=a.getId();
        this.name=a.getName();
        this.type=a.getType();
        this.degree=a.getDegree();
        this.capacity=a.getCapacity();
        this.manufacturer=a.getManufacturer();
        this.price=a.getPrice();
        this.image=a.getImage();
        this.content=a.getContent();
        this.taste_1=a.getTaste_1();
        this.taste_2=a.getTaste_2();
        this.taste_3=a.getTaste_3();
        this.taste_4=a.getTaste_4();
        this.taste_5=a.getTaste_5();
    }
}
