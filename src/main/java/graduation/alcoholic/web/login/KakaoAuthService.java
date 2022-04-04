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
    private final KakaoAPIService kakaoAPIService;
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;
    private final ClientKakao clientKakao;

    @Transactional
    public AuthResponseDto loginToken(String token) {
        //client 정보 가져오기
        UserDto userInfo = kakaoAPIService.getUserInfo(token);

        //기존 회원에서 찾기
        User member = userRepository.findByEmail(userInfo.getEmail());

        //User 객체 만들기
        User kakaoMember=clientKakao.getUserData(userInfo);

        //user_id로 jwt token 생성
        AuthToken appToken = null;

            //만약에 새로운 유저라면 db에 저장 후 토큰 발급
        if (member == null ) {
            userRepository.save(kakaoMember);
            User newMember = userRepository.findByEmail(userInfo.getEmail());

            appToken = authTokenProvider.createUserAppToken(newMember.getId());

            //토큰 발급
            return clientKakao.getAuthResponseDto(newMember,appToken,Boolean.TRUE);
        }else {
            if(member.getDel_cd()!=null){
                //만약 탈퇴한 회원이였다면 닉네임을 이름으로 바꾸고 D를 없앰
                kakaoAPIService.recover(member);
                appToken = authTokenProvider.createUserAppToken(member.getId());
                return clientKakao.getAuthResponseDto(member,appToken,Boolean.TRUE);
            }
        }
        //기존 유저 or 만료시간 완료된 유저라면 새로 토큰 발급

        appToken = authTokenProvider.createUserAppToken(member.getId());

        return clientKakao.getAuthResponseDto(member,appToken,Boolean.FALSE);
    }
}