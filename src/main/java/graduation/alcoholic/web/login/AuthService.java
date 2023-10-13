package graduation.alcoholic.web.login;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.dto.AuthResponseDto;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthTokenProvider authTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthResponseDto createTokenAndResponse(User userInfo,Boolean newMemeber) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getId());
        Authentication authorized=authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String newJwtToken = authTokenProvider.generateToken(authorized,userInfo.getId());

        if (newMemeber){
            return getAuthResponseDto(userInfo,newJwtToken,Boolean.TRUE);
        }
        return getAuthResponseDto(userInfo,newJwtToken,Boolean.FALSE);
    }

    public Long getMemberId(String token) {
        //token 복호화
        String tokenInfo=authTokenProvider.parseClaims(token).getSubject();

        return Long.parseLong(tokenInfo);
    }

    public AuthResponseDto getAuthResponseDto(User userInfo, String jwtToken, Boolean isNewMember){
        return AuthResponseDto.builder()
                .id(userInfo.getId())
                .name(userInfo.getName())
                .nickname(userInfo.getNickname())
                .roletype(userInfo.getRoletype())
                .email(userInfo.getEmail())
                .sex(userInfo.getSex())
                .age_range(userInfo.getAge_range())
                .JwtToken(jwtToken)
                .isNewMember(isNewMember)
                .build();
    }
}