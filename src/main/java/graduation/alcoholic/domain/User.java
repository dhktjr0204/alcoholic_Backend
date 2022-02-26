package graduation.alcoholic.domain;

import graduation.alcoholic.domain.enums.Sex;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    private String age_range;

    private BigDecimal capacity;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<Review>();

    @OneToMany(mappedBy = "id.user_id")
    private List<Zzim> zzims = new ArrayList<>();

    @Builder
    public User(String name, String email, Sex sex, String age_range, BigDecimal capacity) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
    }
}
