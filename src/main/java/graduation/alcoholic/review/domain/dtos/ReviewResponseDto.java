package graduation.alcoholic.review.domain.dtos;

import graduation.alcoholic.domain.Review;

import graduation.alcoholic.domain.enums.Taste;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ReviewResponseDto {

    private Long id;

    private Long user_id;
    private Long alcohol_id;

    private String content;
    private List<String> image;

    private Integer star;

    private Taste taste1;
    private Taste taste2;
    private Taste taste3;
    private Taste taste4;
    private Taste taste5;

    private LocalDateTime modified_date;


    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.user_id = entity.getUser().getId();
        this.alcohol_id = entity.getAlcohol().getId();
        this.content = entity.getContent();
        this.image = StringTofileNameList(entity.getImage());
        this.star = entity.getStar();
        this.taste1 = entity.getTaste1();
        this.taste2 = entity.getTaste2();
        this.taste3 = entity.getTaste3();
        this.taste4 = entity.getTaste4();
        this.taste5 = entity.getTaste5();
        this.modified_date = entity.getModifiedDate();
    }

    public List<String> StringTofileNameList(String fileNameString) {

        if (fileNameString == "") {
            return new ArrayList<String>();
        }
        else
            return new ArrayList<String>(Arrays.asList(fileNameString.split(",")));
    }


}
