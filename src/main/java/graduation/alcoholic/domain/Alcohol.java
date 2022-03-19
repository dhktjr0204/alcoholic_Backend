package graduation.alcoholic.domain;

import graduation.alcoholic.alcohol.AlcoholDetailResponseDto;
import graduation.alcoholic.alcohol.AlcoholResponseDto;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Alcohol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private String name;

    private Double degree;

    private Integer capacity;

    private Integer price;

    private String image;

    private String manufacturer;

    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "taste_1")
    private Taste taste1;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "taste_2")
    private Taste taste2;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "taste_3")
    private Taste taste3;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "taste_4")
    private Taste taste4;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "taste_5")
    private Taste taste5;


    @Builder
    public Alcohol(Long id, Type type, String name, Double degree, Integer capacity, Integer price, String image, String manufacturer, String content, Taste taste1, Taste taste2, Taste taste3, Taste taste4, Taste taste5) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.degree = degree;
        this.capacity = capacity;
        this.price = price;
        this.image = image;
        this.manufacturer = manufacturer;
        this.content = content;
        this.taste1 = taste1;
        this.taste2 = taste2;
        this.taste3 = taste3;
        this.taste4 = taste4;
        this.taste5 = taste5;
    }

    public static AlcoholResponseDto toBoardResponseDto(Alcohol a) {
        return new AlcoholResponseDto(a);
    }

    public AlcoholDetailResponseDto toBoardDetailResponseDto (Alcohol a) {
        return new AlcoholDetailResponseDto(a);
    }
}
