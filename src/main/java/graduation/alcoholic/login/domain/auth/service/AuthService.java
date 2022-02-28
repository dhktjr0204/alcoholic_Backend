package graduation.alcoholic.login.domain.auth.service;

import graduation.alcoholic.login.domain.auth.dto.AuthResponse;
import graduation.alcoholic.login.domain.auth.jwt.AuthToken;
import graduation.alcoholic.login.domain.auth.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.member.User;
import graduation.alcoholic.login.domain.member.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthTokenProvider authTokenProvider;
    private final UserRepository userRepository;

    public AuthResponse updateToken(AuthToken authToken) {
        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        String socialId = claims.getSubject();

        AuthToken newAppToken = authTokenProvider.createUserAppToken(socialId);

        return AuthResponse.builder()
                .JwtToken(newAppToken.getToken())
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