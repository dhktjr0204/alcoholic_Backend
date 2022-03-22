package graduation.alcoholic.alcohol;

import graduation.alcoholic.mypage.zzim.ZzimService;
import graduation.alcoholic.alcohol.Visit.VisitAnalysisService;
import graduation.alcoholic.alcohol.Visit.VisitDto;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.jwt.AuthToken;
import graduation.alcoholic.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.login.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class AlcoholController {


    private final AlcoholService alcoholService;
    private final UserRepository userRepository;
    private final ZzimService zzimService;
    private final AuthTokenProvider authTokenProvider;
    private final VisitAnalysisService visitService;

    @ResponseBody
    @GetMapping("/board")
    public Page<AlcoholResponseDto> getBoard (String type,
                                              Double degreeFrom, Double degreeTo,
                                              Integer priceFrom, Integer priceTo,
                                              @PageableDefault(size = 12) Pageable p
                                             ) {
        Pageable pageable = PageRequest.of(p.getPageNumber(),p.getPageSize(), Sort.by("name"));

        if (type.equals("전체")) {
          return alcoholService.findByPriceAndDegree(priceFrom, priceTo, degreeFrom, degreeTo,pageable);
        }

        //주종, 가격대, 도수에 의한 검색
        else {
            return alcoholService.findByTypeAndPriceAndDegree(
                    type, priceFrom, priceTo, degreeFrom, degreeTo,pageable);
        }
    }

    @ResponseBody
    @GetMapping("/board/search")
    public Page<AlcoholResponseDto> searchByName (@RequestParam String name, Pageable p) {
        Pageable pageable = PageRequest.of(p.getPageNumber(),p.getPageSize(), Sort.by("name"));

        return  alcoholService.searchByName(name, pageable);

    }

    //상세페이지
    @ResponseBody
    @GetMapping("/board/{a_id}")
    public Map<String,Object> getBoardDetail (@PathVariable Long a_id, HttpServletRequest request, Pageable pageable) {
        Map<String, Object> res = new HashMap<>();

        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        boolean isZzimed = false;
        if (jwtToken!= null) {
            AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
            String userEmail = authToken.findTokentoEmail();
            Long u_id = userRepository.findByEmail(userEmail).getId();

            isZzimed = zzimService.findZzim(u_id, a_id);

            alcoholService.printLog(u_id, a_id);

        }

        VisitDto visitInfo = visitService.getVisitInfo(a_id);
        AlcoholDetailResponseDto alcoholDetail = alcoholService.getAlcoholDetail(a_id);//술 객체 가져오기
        res.put("alcoholDetail", alcoholDetail);
        res.put("zzim",isZzimed ); //사용자의 찜여부
        res.put("visit",visitInfo);

        return res;
    }

    @ResponseBody
    @PostMapping("/board/{a_id}") //찜하기 기능
    public HttpStatus addZzim (@PathVariable Long a_id, HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        System.out.println("Jwt token:"+jwtToken);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        String userEmail =authToken.findTokentoEmail();
        //현재 로그인한 유저의 이메일

        User user = userRepository.findByEmail(userEmail);
        HttpStatus httpStatus = zzimService.addZzim(user, a_id);

        return httpStatus; // OK or ALREADY_REPORTED
    }

    @ResponseBody
    @DeleteMapping("/board/{a_id}") //찜삭제
    public HttpStatus deleteZzim (@PathVariable Long a_id, HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        System.out.println("Jwt token:"+jwtToken);
        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
        String userEmail =authToken.findTokentoEmail();
        //현재 로그인한 유저의 이메일

        Long u_id = userRepository.findByEmail(userEmail).getId();

        return zzimService.deleteMyZzim(u_id,a_id);

    }


}
