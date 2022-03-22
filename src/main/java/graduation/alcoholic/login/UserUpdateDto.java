package graduation.alcoholic.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@NoArgsConstructor
public class UserUpdateDto {
    private String nickname;
    private BigDecimal capacity;

    @Builder
    public UserUpdateDto(String nickname, BigDecimal capacity) {
        this.nickname = nickname;
        this.capacity = capacity;
    }
}
