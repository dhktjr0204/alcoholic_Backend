package graduation.alcoholic.domain.entity;

import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class AlcoholTaste {

    @Id
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    private Double degree;

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
    public AlcoholTaste(Long id, Type type, Double degree, Taste taste1, Taste taste2, Taste taste3, Taste taste4, Taste taste5) {
        this.id = id;
        this.type = type;
        this.degree = degree;
        this.taste1 = taste1;
        this.taste2 = taste2;
        this.taste3 = taste3;
        this.taste4 = taste4;
        this.taste5 = taste5;
    }
}
