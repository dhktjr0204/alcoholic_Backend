package graduation.alcoholic.web.bar.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class BarUpdateRequestDto {
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "location")
    private String location;

    @Column(name = "image")
    private String image;

    @Builder
    public BarUpdateRequestDto(String title, String content, String location, String image) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.image = image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}