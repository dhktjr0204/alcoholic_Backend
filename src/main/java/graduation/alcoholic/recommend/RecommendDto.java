package graduation.alcoholic.recommend;


import com.fasterxml.jackson.annotation.JsonProperty;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
public class RecommendDto {
    @JsonProperty("type")
    private Type type;
    @JsonProperty("start_degree")
    private Double start_degree;
    @JsonProperty("end_degree")
    private Double end_degree;
    @JsonProperty("start_price")
    private Integer start_price;
    @JsonProperty("end_price")
    private Integer end_price;

    @JsonProperty("taste_1")
    private Taste taste_1;
    @JsonProperty("taste_2")
    private Taste taste_2;
    @JsonProperty("taste_3")
    private Taste taste_3;
    @JsonProperty("taste_4")
    private Taste taste_4;
    @JsonProperty("taste_5")
    private Taste taste_5;

}
