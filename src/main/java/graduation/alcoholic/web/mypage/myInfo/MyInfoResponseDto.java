package graduation.alcoholic.web.mypage.myInfo;

import graduation.alcoholic.domain.entity.User;
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
    public MyInfoResponseDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.sex = user.getSex();
        this.age_range = user.getAge_range();
        this.capacity = user.getCapacity();
        this.nickname = user.getNickname();
    }
}
