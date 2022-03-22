package graduation.alcoholic.mypage.myInfo;


import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.jwt.AuthToken;
import graduation.alcoholic.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.jwt.JwtHeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class myInfoController {

    private final AuthTokenProvider authTokenProvider;
    private final MyInfoService myInfoService;


    @GetMapping("/myInfo")
    public MyInfoResponseDto getMyInfo(HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        String userEmail =authToken.findTokentoEmail();
        //현재 로그인한 유저의 이메일

        return myInfoService.getUserInfoDto(userEmail);


    }

    @PostMapping("/myInfo")
    public MyInfoResponseDto modifyMyInfo (HttpServletRequest request,
                                           BigDecimal capacity,
                                           String nickname) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        String userEmail =authToken.findTokentoEmail();

        User userInfoEntity = myInfoService.getUserInfoEntity(userEmail);
        return myInfoService.updateCapacityAndNickname(userInfoEntity,capacity,nickname);
    }

}
