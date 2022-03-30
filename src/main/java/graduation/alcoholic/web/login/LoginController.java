package graduation.alcoholic.web.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.dto.ApiResponseDto;
import graduation.alcoholic.web.login.dto.AuthResponseDto;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.web.login.dto.UserDto;
import graduation.alcoholic.domain.repository.UserRepository;
import graduation.alcoholic.web.login.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private final KakaoAuthService kakaoAuthService;
    private final AuthTokenProvider authTokenProvider;
    private final AuthService authService;
    private final UserRepository userRepository;
    @Autowired
    private KakaoAPIService kakaoService;

    private int counter=0;
    private AuthResponseDto FrontInfo=null;




    @ResponseBody
    @GetMapping("/login")
    public ResponseEntity<AuthResponseDto> login(HttpServletRequest request, HttpSession session) {
        ++counter;
        if(counter==1) {
            String code = request.getParameter("code");
            //카카오 토큰 얻기
            String access_Token = kakaoService.getAccessToken(code);
            //카카오 토큰으로 정보 얻어서 dto에 저장
            UserDto userInfo = kakaoService.getUserInfo(access_Token);
            //db에 저장/return할 정보 정제
            FrontInfo = kakaoAuthService.loginToken(access_Token);
            //db에 저장된 유저가져오기
            User user= userRepository.findByEmail(userInfo.getEmail());
            if(user.getAge_range()!=userInfo.getAge_range()){
                System.out.println("유저 나이대 변경");
                kakaoService.update_UserAge(user, userInfo);
            }

            session.setAttribute("email", userInfo.getEmail());
            session.setAttribute("access_Token", access_Token);
            //JWT 토큰 만듬
            }
            ResponseEntity<AuthResponseDto> responseEntity = ApiResponseDto.success(FrontInfo);
            System.out.println("JWT토큰 만듬->" + FrontInfo.getJwtToken());

            //JWT토큰 헤더에 담아 전달
            return responseEntity;
    }

    @GetMapping(value = "/logout")
    public @ResponseBody String logout(HttpSession session) {
        counter=0;
        kakaoService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("email");
        return "logout 완료";
    }

    @PutMapping(value = "/delete")
    public @ResponseBody String delete(HttpSession session) {
        counter=0;
        System.out.println("탈퇴 확인:"+(String)session.getAttribute("access_Token")+(String) session.getAttribute("email"));
        kakaoService.kakaoDelete((String)session.getAttribute("access_Token"));
        User userInfo= userRepository.findByEmail((String) session.getAttribute("email"));
        kakaoService.delete(userInfo);
        session.removeAttribute("access_Token");
        session.removeAttribute("email");
        return "탈퇴 완료";
    }


    @PutMapping("/rename")
    public @ResponseBody String rename(@RequestPart("userUpdateDto")UserUpdateDto userUpdateDto,HttpSession httpSession){
        User userInfo= userRepository.findByEmail((String) httpSession.getAttribute("email"));
        kakaoService.update_Nickname(userInfo,userUpdateDto);
        return "닉네임 생성 완료";
    }


    /**
     * appToken 갱신
     * @return ResponseEntity<AuthResponse>
     */
    @GetMapping("/refresh")
    public @ResponseBody  ResponseEntity<AuthResponseDto> refreshToken (HttpServletRequest request) {
        String jwtToken=JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        if (!authToken.validate()) { // 형식에 맞지 않는 token
            return ApiResponseDto.forbidden(null);
        }
        AuthResponseDto authResponse = authService.updateToken(authToken);
        if (authResponse == null) { // token 만료
            return ApiResponseDto.forbidden(null);
        }
        return ApiResponseDto.success(authResponse);
    }
}