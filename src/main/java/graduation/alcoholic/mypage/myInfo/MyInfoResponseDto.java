package graduation.alcoholic.mypage.myInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MyInfoResponseDto {
    private String name;
    private String email;
    private String sex;
    private String age_range;
    private BigDecimal capacity;
    private String nickname;

    @Builder
    public MyInfoResponseDto(String name, String email, String age_range, BigDecimal capacity, String sex, String nickname) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
        this.nickname = nickname;
    }
}
