package graduation.alcoholic.login.domain.member;

import graduation.alcoholic.login.domain.auth.enumerate.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private String sex;
    private String age_range;
    private String capacity;
    private RoleType roletype;

    @Builder
    public UserDto(String name, String email, String age_range, String capacity, String sex, RoleType roletype) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
        this.roletype = roletype;
    }
}