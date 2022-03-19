package graduation.alcoholic.recommendation;

import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class AlcoholTaste {

    @Id
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Enumerated(value = EnumType.STRING)
    private Taste taste_1;
    @Enumerated(value = EnumType.STRING)
    private Taste taste_2;
    @Enumerated(value = EnumType.STRING)
    private Taste taste_3;
    @Enumerated(value = EnumType.STRING)
    private Taste taste_4;
    @Enumerated(value = EnumType.STRING)
    private Taste taste_5;


    @Builder
    public AlcoholTaste(Long id, Taste taste_1, Taste taste_2, Taste taste_3, Taste taste_4, Taste taste_5) {
        this.id = id;
        this.taste_1 = taste_1;
        this.taste_2 = taste_2;
        this.taste_3 = taste_3;
        this.taste_4 = taste_4;
        this.taste_5 = taste_5;
    }
}
