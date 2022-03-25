package graduation.alcoholic.web.bar.dto;

import graduation.alcoholic.domain.entity.Bar;
import graduation.alcoholic.domain.entity.User;
import lombok.Builder;

public class BarSaveRequestDto {
    private User user;
    private String title;
    private String content;
    private String location;
    private String image;

    @Builder
    public BarSaveRequestDto(User user, String title, String content, String location, String image) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.location = location;
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
                .content(content)
                .image(image)
                .build();
    }
}
