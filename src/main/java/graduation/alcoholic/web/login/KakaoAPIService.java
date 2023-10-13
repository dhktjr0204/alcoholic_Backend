package graduation.alcoholic.web.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;


import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.domain.enums.RoleType;
import graduation.alcoholic.domain.repository.UserRepository;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.dto.ApiResponseDto;
import graduation.alcoholic.web.login.dto.AuthResponseDto;
import graduation.alcoholic.web.login.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class KakaoAPIService {
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;
    private final AuthService authService;
    @Value("${kakao.login.client_id}")
    private String client_id;

    @Transactional
    public ResponseEntity<AuthResponseDto> login(HttpServletRequest request){
        //카카오 access_Token 가져오기
        String access_Token=getAccessToken(request);
        //카카오 access_Token으로 유저 정보 가져옴
        User userInfo = getUserInfo(access_Token);
        //기존 회원이면 업데이트만, 새로운 회원이면 저장, jwt토큰 발급
        AuthResponseDto responseUser = saveOrUpdate(userInfo);
        //세션저장
        HttpSession session=request.getSession();
        session.setAttribute("email", responseUser.getEmail());
        session.setAttribute("access_Token", access_Token);

        ResponseEntity<AuthResponseDto> responseEntity = ApiResponseDto.success(responseUser);
        //JWT토큰 헤더에 담아 전달
        return responseEntity;
    }

    private AuthResponseDto saveOrUpdate(User userInfo) {
        Optional<User> user = userRepository.findByEmail(userInfo.getEmail());
        AuthResponseDto authResponseDto =null;
        if (user.isPresent()) {
            //연령대 업데이트
            User oldUser =userRepository.findByEmail(userInfo.getEmail()).orElseThrow(()->new IllegalArgumentException());
            //탈퇴한 회원이면 del_cd null로 바꿈
            if (oldUser.getDel_cd()!=null){
                oldUser.updateDelete(null);
            }
            authResponseDto = authService.createTokenAndResponse(user.get(),Boolean.FALSE); // 사용자가 이미 존재하는 경우
        } else {
            User saveUser = userRepository.save(userInfo);
            //토큰 발급
            authResponseDto = authService.createTokenAndResponse(saveUser,Boolean.TRUE);// 사용자가 존재하지 않는 경우
        }
        return authResponseDto;
    }

    //카카오 access_Token 토큰 가져오기
    public String getAccessToken (HttpServletRequest request) {
        String authorize_code=getCode(request);
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //	POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+client_id);
            sb.append("&redirect_uri=http://localhost:3000/auth/login");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //	결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("토큰 얻기 responseCode : " + responseCode);

            //	요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return access_Token;
    }

    //사용자 정보 가져오기
    public User getUserInfo (String access_Token) {
        User userInfo = null;
        //	요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //	요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("정보 얻기 responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            final String authorizationHeader = conn.getHeaderField("Authorization");

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String username = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String age_range =null;
            String sex=null;
            try {
                age_range = kakao_account.getAsJsonObject().get("age_range").getAsString();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                sex = kakao_account.getAsJsonObject().get("gender").getAsString();
            }catch (Exception e){
                e.printStackTrace();
            }

            userInfo = User.builder()
                    .name(username)
                    .nickname(username)
                    .email(email)
                    .sex(sex)
                    .age_range(age_range)
                    .capacity(new BigDecimal(0))
                    .roletype(RoleType.USER)
                    .del_cd(null)
                    .build();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userInfo;
    }

    public void kakaoLogout(HttpSession session) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        connectKaKao(reqURL,session);
    }

    @Transactional
    public void kakaoDelete(HttpSession session) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        //del_cd는 D로 바꿈(del_cd가 D라면 닉네임이 탈퇴한 유저입니다. 로 뜬다.)
        User userInfo= userRepository.findByEmail((String) session.getAttribute("email"))
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다"));
        userInfo.updateDelete("D");
        connectKaKao(reqURL,session);
    }
    public void connectKaKao(String reqURL,HttpSession session){
        //세션에 저장된 카카오 access_token
        String access_Token=(String)session.getAttribute("access_Token");
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = "";
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);

            session.removeAttribute("access_Token");
            session.removeAttribute("email");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //닉네임 업데이트
    @Transactional
    public void update_Nickname(Long id, UserUpdateDto userUpdateDto){
        User userInfo=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 없습니다"+id));
        userInfo.signInUpdate(userUpdateDto.getNickname(),userUpdateDto.getCapacity());
    }

    public String getCode(HttpServletRequest request){
        String code = request.getParameter("code");
        return code;
    }
}