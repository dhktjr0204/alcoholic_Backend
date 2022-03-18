package graduation.alcoholic.domain;

import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

////    @Formula("(select count(*) from review r where r.a_id = a_id)")
////    private int reviewCount;


    @Builder
//    public Alcohol(Type type, String name, Integer price, String image, String manufacturer, String content, Double degree, Integer capacity,
//                   Taste taste_1, Taste taste_2, Taste taste_3, Taste taste_4, Taste taste_5) {
//        this.type = type;
//        this.name = name;
//        this.price = price;
//        this.image = image;
//        this.manufacturer = manufacturer;
//        this.content = content;
//        this.degree = degree;
//        this.capacity = capacity;
//        this.taste_1 = taste_1;
//        this.taste_2 = taste_2;
//        this.taste_3 = taste_3;
//        this.taste_4 = taste_4;
//        this.taste_5 = taste_5;
//    }

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
}
