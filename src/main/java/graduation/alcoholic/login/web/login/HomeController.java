package graduation.alcoholic.login.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import graduation.alcoholic.login.domain.auth.dto.ApiResponse;
import graduation.alcoholic.login.domain.auth.dto.AuthResponse;
import graduation.alcoholic.login.domain.auth.jwt.AuthToken;
import graduation.alcoholic.login.domain.auth.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.auth.jwt.JwtHeaderUtil;
import graduation.alcoholic.login.domain.auth.service.AuthService;
import graduation.alcoholic.login.domain.auth.service.KakaoAuthService;
import graduation.alcoholic.login.domain.member.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class HomeController {
    private final KakaoAuthService kakaoAuthService;
    private final AuthTokenProvider authTokenProvider;
    private final AuthService authService;
    @Autowired
    private KakaoAPI kakao;

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(HttpServletRequest request, HttpSession session) {
        String code=request.getParameter("code");
        System.out.println("Code 받았어~~"+code);
        String access_Token = kakao.getAccessToken(code);
        User userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        AuthResponse jwtToken=kakaoAuthService.loginToken(access_Token);
        //JWT 토큰 만듬
        ResponseEntity<AuthResponse> responseEntity= ApiResponse.success(jwtToken);
        System.out.println("JWT토큰 만듬->"+jwtToken.getJwtToken());
     //	클라이언트의 이메일이 존재하면 세션에 해당 이메일과 토큰 등록
        if (userInfo.getEmail() != null) {
            session.setAttribute("email", userInfo.getEmail());
            session.setAttribute("access_Token", access_Token);
        }
        //JWT토큰 헤더에 담아 전달
        return responseEntity;
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakao.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("email");
        return "login";
    }


    /**
     * appToken 갱신
     * @return ResponseEntity<AuthResponse>
     */
    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken (HttpServletRequest request) {
        String jwtToken=JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        if (!authToken.validate()) { // 형식에 맞지 않는 token
            return ApiResponse.forbidden(null);
        }
        AuthResponse authResponse = authService.updateToken(authToken);
        if (authResponse == null) { // token 만료
            return ApiResponse.forbidden(null);
        }
        return ApiResponse.success(authResponse);
    }
}