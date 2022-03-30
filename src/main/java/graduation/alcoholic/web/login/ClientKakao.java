package graduation.alcoholic.web.login;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.dto.AuthResponseDto;
import graduation.alcoholic.web.login.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class ClientKakao {
    public User getUserData(UserDto userInfo) {
        return User.builder()
                .name(userInfo.getName())
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .sex(userInfo.getSex())
                .age_range(userInfo.getAge_range())
                .capacity(null)
                .build();
    }
    public AuthResponseDto getAuthResponseDto(User userInfo, AuthToken appToken, Boolean isNewMember){
        return AuthResponseDto.builder()
                .id(userInfo.getId())
                .name(userInfo.getName())
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .sex(userInfo.getSex())
                .age_range(userInfo.getAge_range())
                .JwtToken(appToken.getToken())
                .isNewMember(isNewMember)
                .build();
    }
}
