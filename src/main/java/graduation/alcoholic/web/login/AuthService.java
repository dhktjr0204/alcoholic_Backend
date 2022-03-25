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

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthTokenProvider authTokenProvider;
    private final UserRepository userRepository;
    private final ClientKakao clientKakao;

    public AuthResponseDto updateToken(AuthToken authToken) {
        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }
        String socialId = claims.getSubject();
        AuthToken newAppToken = authTokenProvider.createUserAppToken(Long.parseLong(socialId));
        User UserInfo = userRepository.findById(Long.parseLong(socialId))
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id" +socialId));;

        return clientKakao.getAuthResponseDto(UserInfo,newAppToken,Boolean.FALSE);
    }

    public Long getMemberId(String token) {
        AuthToken authToken = authTokenProvider.convertAuthToken(token);

        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        try {
            return Long.parseLong(claims.getSubject());

        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다.");
        }
    }
}