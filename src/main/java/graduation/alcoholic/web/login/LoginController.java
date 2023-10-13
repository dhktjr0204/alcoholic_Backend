package graduation.alcoholic.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import graduation.alcoholic.web.login.dto.*;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private final AuthTokenProvider authTokenProvider;
    private final AuthService authService;
    private final UserRepository userRepository;
    @Autowired
    private KakaoAPIService kakaoService;

    private AuthResponseDto FrontInfo=null;

    @GetMapping("/login")
    public ResponseEntity<AuthResponseDto> login(HttpServletRequest request) {
        return kakaoService.login(request);
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakaoService.kakaoLogout(session);
        return "logout 완료";
    }

    @PutMapping(value = "/delete")
    public String delete(HttpSession session) {
        kakaoService.kakaoDelete(session);
        return "탈퇴 완료";
    }

    @PutMapping("/rename")
    public String rename(HttpServletRequest request,@RequestPart("userUpdateDto")UserUpdateDto userUpdateDto){
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);
        kakaoService.update_Nickname(u_id, userUpdateDto);
        return "닉네임 생성 완료";
    }
}