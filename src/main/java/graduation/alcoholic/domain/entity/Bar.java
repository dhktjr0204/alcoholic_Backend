
package graduation.alcoholic.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Bar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "location")
    private String location;


    @Column(name = "location_detail")
    private String location_detail;

    @Column(name = "image")
    private String image;

    @Builder
    public Bar(User user, String title, String content, String location, String location_detail,String image) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.location = location;
        this.location_detail=location_detail;
        this.image = image;
    }

    public void update(String title, String content, String location, String location_detail,String image){
        this.title=title;
        this.content=content;
        this.location=location;
        this.location_detail=location_detail;
        this.image=image;
    }

    public void setDel(){
        this.user=null;
    }
}

