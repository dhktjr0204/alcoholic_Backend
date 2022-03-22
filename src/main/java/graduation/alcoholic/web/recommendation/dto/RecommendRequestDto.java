package graduation.alcoholic.web.recommendation.dto;

import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendRequestDto {

    private Type type;

    private String degree;

    private Taste taste1;
    private Taste taste2;
    private Taste taste3;
    private Taste taste4;
    private Taste taste5;


    @Builder
    public RecommendRequestDto(Type type, String degree, Taste taste1, Taste taste2, Taste taste3, Taste taste4, Taste taste5) {
        this.type = type;
        this.degree = degree;
        this.taste1 = taste1;
        this.taste2 = taste2;
        this.taste3 = taste3;
        this.taste4 = taste4;
        this.taste5 = taste5;
    }
}
