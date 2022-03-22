package graduation.alcoholic.web.login;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.dto.AuthResponseDto;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthTokenProvider authTokenProvider;
    private final UserRepository userRepository;

    public AuthResponseDto updateToken(AuthToken authToken) {
        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        String socialId = claims.getSubject();
        AuthToken newAppToken = authTokenProvider.createUserAppToken(socialId);
        User UserInfo = userRepository.findByEmail(socialId);

        return AuthResponseDto.builder()
                .name(UserInfo.getName())
                .email(UserInfo.getEmail())
                .sex(UserInfo.getSex())
                .age_range(UserInfo.getAge_range())
                .JwtToken(newAppToken.getToken())
                .isNewMember(Boolean.FALSE)
                .build();
    }

    public String getMemberId(String token) {
        AuthToken authToken = authTokenProvider.convertAuthToken(token);

        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        try {
            User member =  userRepository.findByEmail(claims.getSubject());
            return member.getEmail();

        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.");
        }
    }
}