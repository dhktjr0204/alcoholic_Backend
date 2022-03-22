package graduation.alcoholic.web.mypage.zzim;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ZzimController {


    private final ZzimService zzimService;
    private final UserRepository userRepository;
    private final AuthTokenProvider authTokenProvider;



    @GetMapping("/myZzim")
    public List<ZzimResponseDto> getMyZzimList (HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        String userEmail =authToken.findTokentoEmail();
        //현재 로그인한 유저의 이메일

        Long u_id =  userRepository.findByEmail(userEmail).getId();

        List<Alcohol> myZzim = zzimService.getMyZzim(u_id);
        List<ZzimResponseDto> res = new ArrayList<>();

        for (Alcohol a : myZzim) {
            res.add(new ZzimResponseDto(a.getId(),a.getImage(),a.getName()));
        }
        return res;
    }


    @DeleteMapping("/myZzim/{a_id}")
    public HttpStatus deleteMyZzim (@PathVariable Long a_id, HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        Map<String,String> res=new HashMap<>();
        String userEmail =authToken.findTokentoEmail();
        //현재 로그인한 유저의 이메일
        Long u_id=  userRepository.findByEmail(userEmail).getId();
        return zzimService.deleteMyZzim(u_id,a_id);
    }

}
