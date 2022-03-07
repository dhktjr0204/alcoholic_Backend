package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.User;
import lombok.Builder;

import javax.persistence.Column;

public class BarSaveDTO {
    private User user;
    private String title;
    private String content;
    private String location;
    private String image;

    @Builder
    public BarSaveDTO(User user, String title, String content, String location, String image) {
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
