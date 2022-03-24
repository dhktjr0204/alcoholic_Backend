package graduation.alcoholic.web.login;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.dto.AuthResponseDto;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.dto.UserDto;
import graduation.alcoholic.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class KakaoAuthService {
    @Autowired
    private final KakaoAPIService kakaoAPI;
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;
    private final ClientKakao clientKakao;

    @Transactional
    public AuthResponseDto loginToken(String token) {
        //client 정보 가져오기
        UserDto userInfo = kakaoAPI.getUserInfo(token);

        //유저 객체 만들기
        User kakaoMember=clientKakao.getUserData(userInfo);
        String email = kakaoMember.getEmail();

        //기존 회원에서 찾기
        User member = userRepository.findByEmail(email);

        //user_id로 jwt token 생성
        AuthToken appToken = authTokenProvider.createUserAppToken(kakaoMember.getEmail());

        //만약에 새로운 유저라면 db에 저장 후 토큰 발급
        if (member == null) {
            userRepository.save(kakaoMember);
            //토큰 발급
            return AuthResponseDto.builder()
                    .id(kakaoMember.getId())
                    .name(kakaoMember.getName())
                    .nickname(kakaoMember.getNickname())
                    .email(kakaoMember.getEmail())
                    .sex(kakaoMember.getSex())
                    .age_range(kakaoMember.getAge_range())
                    .JwtToken(appToken.getToken())
                    .isNewMember(Boolean.TRUE)
                    .build();
        }
        //기존 유저 or 만료시간 완료된 유저라면 새로 토큰 발급
        return AuthResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .sex(member.getSex())
                .age_range(member.getAge_range())
                .JwtToken(appToken.getToken())
                .isNewMember(Boolean.FALSE)
                .build();
    }
}