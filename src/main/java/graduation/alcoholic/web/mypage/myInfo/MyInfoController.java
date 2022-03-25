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


    @GetMapping("/myInfo")
    public MyInfoResponseDto getMyInfo(HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

        MyInfoResponseDto userInfoDto = myInfoService.getUserInfoDto(u_id);
        System.out.println("capacity = " + userInfoDto.getCapacity());
        return userInfoDto;


    }

    @PostMapping("/myInfo")
    public MyInfoResponseDto modifyMyInfo (HttpServletRequest request,
                                           @RequestBody MyInfoUpdateDto updateDto) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

        User userInfoEntity = myInfoService.getUserInfoEntity(u_id);
        System.out.println("capacity = " + updateDto.getCapacity());
        System.out.println("nickname = " + updateDto.getNickname());
        return myInfoService.updateCapacityAndNickname(userInfoEntity,updateDto.getCapacity(), updateDto.getNickname());
    }

}
