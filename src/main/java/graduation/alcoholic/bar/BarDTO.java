package graduation.alcoholic.bar;

import graduation.alcoholic.domain.User;
import lombok.Data;

import javax.persistence.Column;

@Data
public class BarDTO {
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "location")
    private String location;
    @Column(name = "image")
    private String image;
}
