package graduation.alcoholic.web.mypage.myInfo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MyInfoUpdateDto {
    private String nickname;
    private BigDecimal capacity;

    public MyInfoUpdateDto(String nickname, BigDecimal capacity) {
        this.capacity=capacity;
        this.nickname=nickname;
    }

    public MyInfoUpdateDto () {

    }
}
