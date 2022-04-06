package graduation.alcoholic.web.mypage.myInfo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MyInfoUpdateDto {
    private String nickname;
    private BigDecimal capacity;

    public MyInfoUpdateDto () {
    }

    public MyInfoUpdateDto (String nickname) {
        this.nickname=nickname;
    }

    public MyInfoUpdateDto (BigDecimal capacity) {
        this.capacity=capacity;
    }
}
