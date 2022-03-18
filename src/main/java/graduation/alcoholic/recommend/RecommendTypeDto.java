package graduation.alcoholic.recommend;

import graduation.alcoholic.domain.enums.Taste;
import lombok.Builder;

public class RecommendTypeDto {
    private String taste_1;
    private String taste_2;
    private String taste_3;
    private String taste_4;
    private String taste_5;

    @Builder
    public RecommendTypeDto(String taste_1, String taste_2, String taste_3, String taste_4, String taste_5) {
        this.taste_1 = taste_1;
        this.taste_2 = taste_2;
        this.taste_3 = taste_3;
        this.taste_4 = taste_4;
        this.taste_5 = taste_5;
    }
}
