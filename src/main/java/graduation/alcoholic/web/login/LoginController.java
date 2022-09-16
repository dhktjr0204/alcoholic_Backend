package graduation.alcoholic.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.dto.*;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final KakaoAuthService kakaoAuthService;
    private final AuthTokenProvider authTokenProvider;
    private final AuthService authService;
    private final UserRepository userRepository;
    @Autowired
    private KakaoAPIService kakaoService;

    private AuthResponseDto FrontInfo=null;

    @GetMapping("/auth/login")
    public AuthResponseDto login(HttpServletRequest request) {
        String code = request.getParameter("code");
        //카카오 토큰 얻기
        String access_Token = kakaoService.getAccessToken(code);
        //카카오 토큰으로 정보 얻어서 dto에 저장
        UserDto userInfo = kakaoService.getUserInfo(access_Token);
        //db에 저장, return할 정보 정제
        FrontInfo = kakaoAuthService.loginToken(access_Token);
        //db에 저장된 유저가져오기
        User user= userRepository.findByEmail(userInfo.getEmail());
        if(!user.getAge_range().equals(userInfo.getAge_range())){
                System.out.println("유저 나이대 변경");
                kakaoService.update_UserAge(user, userInfo);
        }
        //세션저장
        HttpSession session=request.getSession();
        session.setAttribute("email", userInfo.getEmail());
        session.setAttribute("access_Token", access_Token);
        System.out.println("로그인 세션 저장 확인용: "+request.getSession().getAttribute("email")+" "+request.getSession().getAttribute("access_Token"));
        //JWT 토큰 만듬
        AuthResponseDto responseEntity = FrontInfo;
        System.out.println("JWT토큰 만듬->" + FrontInfo.getJwtToken());
        System.out.println("return할 데이터 잘 들어갔는지 확인용(email만 뽑았어요)" + responseEntity.getEmail());

        //JWT토큰 헤더에 담아 전달
        return responseEntity;
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        kakaoService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("email");
        return "logout 완료";
    }

    @PutMapping("/auth/delete")
    public String delete(HttpSession session) {
        System.out.println("탈퇴 세션 저장 확인용: "+session.getAttribute("email")+" "+session.getAttribute("access_Token"));
        kakaoService.kakaoDelete((String)session.getAttribute("access_Token"));
        User userInfo= userRepository.findByEmail((String) session.getAttribute("email"));
        kakaoService.delete(userInfo);
        session.removeAttribute("access_Token");
        session.removeAttribute("email");
        return "탈퇴 완료";
    }


    @PutMapping("/auth/rename")
    public String rename(@RequestPart("userUpdateDto")UserUpdateDto userUpdateDto,HttpSession session){
        User userInfo= userRepository.findByEmail((String) session.getAttribute("email"));
        kakaoService.update_Nickname(userInfo,userUpdateDto);
        return "닉네임 생성 완료";
    }

    /**
     * appToken 갱신
     * @return ResponseEntity<AuthResponse>
     */
    @GetMapping("/auth/refresh")
    public ResponseEntity<AuthResponseDto> refreshToken (HttpServletRequest request) {
        String jwtToken=JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        if (!authToken.validate(jwtToken)) { // 형식에 맞지 않는 token
            return ApiResponseDto.forbidden(null);
        }
        AuthResponseDto authResponse = authService.updateToken(authToken);
        if (authResponse == null) { // token 만료
            return ApiResponseDto.forbidden(null);
        }
        return ApiResponseDto.success(authResponse);
    }
}