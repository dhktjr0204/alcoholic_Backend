package graduation.alcoholic.web.mypage.myInfo;


import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.AuthService;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class MyInfoController {

    private final AuthTokenProvider authTokenProvider;
    private final MyInfoService myInfoService;
    private final AuthService authService;


    @GetMapping("/myInfo")//회원정보 조회
    public MyInfoResponseDto getMyInfo(HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

        MyInfoResponseDto userInfoDto = myInfoService.getUserInfoDto(u_id);

        return userInfoDto;

    }

    @PostMapping("/myInfo/nickname") //닉네임 수정
    public MyInfoResponseDto modifyNickname (HttpServletRequest request,
                                           @RequestBody MyInfoUpdateDto updateDto) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);
       // System.out.println(nickname);

        User userInfoEntity = myInfoService.getUserInfoEntity(u_id);
        return myInfoService.updateNickname(userInfoEntity,updateDto);
    }

    @PostMapping("/myInfo/capacity") //주량 수정
    public MyInfoResponseDto modifyCapacity (HttpServletRequest request,
                                             @RequestBody MyInfoUpdateDto updateDto) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

        User userInfoEntity = myInfoService.getUserInfoEntity(u_id);
        return myInfoService.updateCapacity(userInfoEntity,updateDto);
    }

}
