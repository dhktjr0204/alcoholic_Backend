package graduation.alcoholic.web.login.dto;

import graduation.alcoholic.web.login.domain.enumerate.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserDto {
    private String name;
    private String nickname;
    private String email;
    private String sex;
    private String age_range;
    private BigDecimal capacity;
    private RoleType roletype;

    @Builder
    public UserDto(String name, String nickname, String email, String age_range, BigDecimal capacity, String sex, RoleType roletype) {
        this.name = name;
        this.nickname=nickname;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
        this.roletype = roletype;
    }
}