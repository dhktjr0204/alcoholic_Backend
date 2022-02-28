package graduation.alcoholic.login.domain.auth.service;

import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.auth.dto.AuthResponse;
import graduation.alcoholic.login.domain.auth.jwt.AuthToken;
import graduation.alcoholic.login.domain.auth.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.member.Member;
import graduation.alcoholic.login.domain.member.UserRepository;
import graduation.alcoholic.login.web.login.ClientKakao;
import graduation.alcoholic.login.web.login.KakaoAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class KakaoAuthService {
    @Autowired
    private final KakaoAPI kakaoAPI;
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;
    private final ClientKakao clientKakao;

    @Transactional
    public AuthResponse loginToken(String token) {
        //client 정보 가져오기
        Member userInfo = kakaoAPI.getUserInfo(token);
        User kakaoMember=clientKakao.getUserData(userInfo);
        String email = kakaoMember.getEmail();
        User member = userRepository.findByEmail(email);

        //jwt token
        AuthToken appToken = authTokenProvider.createUserAppToken(email);

        //만약에 새로운 유저라면 db에 저장 후 토큰 발급
        if (member == null) {
            userRepository.save(kakaoMember);
            //토큰 발급
            return AuthResponse.builder()
                    .JwtToken(appToken.getToken())
                    .isNewMember(Boolean.TRUE)
                    .build();
        }
        //기존 유저 or 만료시간 완료된 유저라면 새로 토큰 발급
        return AuthResponse.builder()
                .JwtToken(appToken.getToken())
                .isNewMember(Boolean.FALSE)
                .build();
    }
}