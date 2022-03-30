package graduation.alcoholic.web.review.dto;

import graduation.alcoholic.domain.entity.Review;

import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ReviewResponseDto {

    private Long id;

    private Long user_id;
    private Long alcohol_id;
    private Type type;

    private String nickname;

    private String content;
    private List<String> image;

    private Integer star;

    private Taste taste1;
    private Taste taste2;
    private Taste taste3;
    private Taste taste4;
    private Taste taste5;

    private String modified_date;


    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.user_id = entity.getUser().getId();
        this.alcohol_id = entity.getAlcohol().getId();
        this.type = entity.getAlcohol().getType();
        this.nickname = entity.getUser().getNickname();
        this.content = entity.getContent();
        this.image = StringTofileNameList(entity.getImage());
        this.star = entity.getStar();
        this.taste1 = entity.getTaste1();
        this.taste2 = entity.getTaste2();
        this.taste3 = entity.getTaste3();
        this.taste4 = entity.getTaste4();
        this.taste5 = entity.getTaste5();
        this.modified_date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(entity.getModifiedDate());
    }

    public List<String> StringTofileNameList(String fileNameString) {

        if (fileNameString == ",") {
            return new ArrayList<String>();
        }
        else {
            List<String> nameList = Arrays.asList(fileNameString.split(","));
            for (int i = 0; i < nameList.size(); i++) {
                nameList.set(i, "https://alcoholic-review.s3.ap-northeast-2.amazonaws.com/" + nameList.get(i));
            }
            return nameList;
        }
    }


}
