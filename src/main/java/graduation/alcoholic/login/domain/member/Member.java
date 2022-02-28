package graduation.alcoholic.login.domain.member;

import graduation.alcoholic.login.domain.auth.enumerate.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Enumerated;

@Getter
@Setter
public class Member {
    private Long id;
    private String name;
    private String email;
    private String sex;
    private String age_range;
    private String capacity;
    private RoleType roletype;

    @Builder
    public Member(Long id, String name, String email, String age_range, String capacity, String sex, RoleType roletype) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.age_range = age_range;
        this.capacity = capacity;
        this.roletype = roletype;
    }
}
