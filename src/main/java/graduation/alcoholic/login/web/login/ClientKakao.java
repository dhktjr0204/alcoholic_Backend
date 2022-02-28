package graduation.alcoholic.login.web.login;

import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.member.Member;
import org.springframework.stereotype.Component;

@Component
public class ClientKakao {
    public User setUserData(Member userInfo) {
        return User.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .sex(userInfo.getSex())
                .age_range(userInfo.getAge_range())
                .capacity(null)
                .build();
    }
}
