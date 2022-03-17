package graduation.alcoholic.review;

import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewTopTaste {

    private Taste top_taste_1;
    private Taste top_taste_2;
    private Taste top_taste_3;
    private Taste top_taste_4;
    private Taste top_taste_5;

    private int top_taste_1_percent;
    private int top_taste_2_percent;
    private int top_taste_3_percent;
    private int top_taste_4_percent;
    private int top_taste_5_percent;

    @Builder
    public ReviewTopTaste(Taste top_taste_1, Taste top_taste_2, Taste top_taste_3, Taste top_taste_4, Taste top_taste_5,
                          int top_taste_1_percent, int top_taste_2_percent, int top_taste_3_percent, int top_taste_4_percent, int top_taste_5_percent) {
        this.top_taste_1 = top_taste_1;
        this.top_taste_2 = top_taste_2;
        this.top_taste_3 = top_taste_3;
        this.top_taste_4 = top_taste_4;
        this.top_taste_5 = top_taste_5;
        this.top_taste_1_percent = top_taste_1_percent;
        this.top_taste_2_percent = top_taste_2_percent;
        this.top_taste_3_percent = top_taste_3_percent;
        this.top_taste_4_percent = top_taste_4_percent;
        this.top_taste_5_percent = top_taste_5_percent;
    }
}
