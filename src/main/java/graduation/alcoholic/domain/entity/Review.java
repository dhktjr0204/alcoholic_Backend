package graduation.alcoholic.domain.entity;

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
    public Review(User user, Alcohol alcohol, String content, String image,
                  Integer star, Taste taste1, Taste taste2, Taste taste3, Taste taste4, Taste taste5) {

        this.user = user;
        this.alcohol = alcohol;
        this.content = content;
        this.image = image;
        this.star = star;
        this.taste1 = taste1;
        this.taste2 = taste2;
        this.taste3 = taste3;
        this.taste4 = taste4;
        this.taste5 = taste5;

    }

    public void update (String content, String image, Integer star, Taste taste1, Taste taste2, Taste taste3, Taste taste4, Taste taste5) {
        this.content = content;
        this.image = image;
        this.star = star;
        this.taste1 = taste1;
        this.taste2 = taste2;
        this.taste3 = taste3;
        this.taste4 = taste4;
        this.taste5 = taste5;
    }

}
