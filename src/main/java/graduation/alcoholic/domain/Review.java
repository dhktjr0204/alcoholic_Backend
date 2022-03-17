package graduation.alcoholic.domain;

import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Alcohol alcohol;

    private String content;

    private String image;

    private Integer star;

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
    public Review(User user, Alcohol alcohol, String content, String image,
                  Integer star, Taste taste_1, Taste taste_2, Taste taste_3, Taste taste_4, Taste taste_5) {

        this.user = user;
        this.alcohol = alcohol;
        this.content = content;
        this.image = image;
        this.star = star;
        this.taste_1 = taste_1;
        this.taste_2 = taste_2;
        this.taste_3 = taste_3;
        this.taste_4 = taste_4;
        this.taste_5 = taste_5;

    }

    public void update (String content, String image, Integer star, Taste taste_1, Taste taste_2, Taste taste_3, Taste taste_4, Taste taste_5) {
        this.content = content;
        this.image = image;
        this.star = star;
        this.taste_1 = taste_1;
        this.taste_2 = taste_2;
        this.taste_3 = taste_3;
        this.taste_4 = taste_4;
        this.taste_5 = taste_5;
    }

}
