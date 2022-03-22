package graduation.alcoholic.web.review.dto;

import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.web.review.dto.ReviewResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewTotalResponseDto {

    private Double total_star;

    private Taste top_taste1;
    private Taste top_taste2;
    private Taste top_taste3;
    private Taste top_taste4;
    private Taste top_taste5;

    private int top_taste1_percent;
    private int top_taste2_percent;
    private int top_taste3_percent;
    private int top_taste4_percent;
    private int top_taste5_percent;

    private List<ReviewResponseDto> reviewResponseDtoList;

    @Builder
    public ReviewTotalResponseDto(Double total_star, Taste top_taste1, Taste top_taste2, Taste top_taste3, Taste top_taste4, Taste top_taste5,
                                  int top_taste1_percent, int top_taste2_percent, int top_taste3_percent, int top_taste4_percent, int top_taste5_percent,
                                  List<ReviewResponseDto> reviewResponseDtoList) {

        this.total_star = total_star;
        this.top_taste1 = top_taste1;
        this.top_taste2 = top_taste2;
        this.top_taste3 = top_taste3;
        this.top_taste4 = top_taste4;
        this.top_taste5 = top_taste5;

        this.top_taste1_percent = top_taste1_percent;
        this.top_taste2_percent = top_taste2_percent;
        this.top_taste3_percent = top_taste3_percent;
        this.top_taste4_percent = top_taste4_percent;
        this.top_taste5_percent = top_taste5_percent;
        this.reviewResponseDtoList = reviewResponseDtoList;
    }

}
