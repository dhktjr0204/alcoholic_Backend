package graduation.alcoholic.review;

import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewTopTaste {

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

    @Builder
    public ReviewTopTaste(Taste top_taste1, Taste top_taste2, Taste top_taste3, Taste top_taste4, Taste top_taste5,
                          int top_taste1_percent, int top_taste2_percent, int top_taste3_percent, int top_taste4_percent, int top_taste5_percent) {
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
    }
}
