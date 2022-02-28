package graduation.alcoholic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Bar extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    private String content;

    private String location;

    private String image;

    @Builder
    public Bar(User user, String title, String content, String location, String image) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.location = location;
        this.image = image;
    }
}