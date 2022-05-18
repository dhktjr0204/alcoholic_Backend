package graduation.alcoholic.web.bar.dto;

import graduation.alcoholic.domain.entity.Bar;

import graduation.alcoholic.domain.entity.User;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
public class BarSaveRequestDto {
    private User user;
    private String title;
    private String content;
    private String location;
    private String location_detail;
    private String image;

    @Builder
    public BarSaveRequestDto(User user, String title, String content, String location,String location_detail, String image) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.location = location;
        this.location_detail=location_detail;
        this.image = image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public Bar toEntity() {
        return Bar.builder()
                .user(user)
                .title(title)
                .location(location)
                .location_detail(location_detail)
                .content(content)
                .image(image)
                .build();
    }
}
