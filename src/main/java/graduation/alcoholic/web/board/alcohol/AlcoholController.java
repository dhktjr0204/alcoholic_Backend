package graduation.alcoholic.web.board.alcohol;

import graduation.alcoholic.web.board.alcohol.dto.AlcoholDetailResponseDto;
import graduation.alcoholic.web.board.alcohol.dto.AlcoholResponseDto;
import graduation.alcoholic.web.login.AuthService;
import graduation.alcoholic.web.mypage.zzim.ZzimService;
import graduation.alcoholic.web.board.visit.VisitAnalysisService;
import graduation.alcoholic.web.board.visit.dto.VisitDto;
import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.web.login.domain.jwt.AuthToken;
import graduation.alcoholic.web.login.domain.jwt.AuthTokenProvider;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.domain.repository.UserRepository;
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
    private final AuthService authService;
    private final Double ALCOHOL_IN_SOJU = 16.9*360*0.8; //소주에 들어있는 알코올량 (g)

    @ResponseBody
    @GetMapping("/board")
    public Page<AlcoholResponseDto> getBoard (String type, Double degreeFrom, Double degreeTo,
                                              Integer priceFrom, Integer priceTo, @PageableDefault(size = 12) Pageable p) {
        Pageable pageable = PageRequest.of(p.getPageNumber(),p.getPageSize(), Sort.by("name")); // 가나다순 정렬

        if (type.equals("전체")) {
          return alcoholService.findByPriceAndDegree(priceFrom, priceTo, degreeFrom, degreeTo,pageable);
        }

        else {
            return alcoholService.findByTypeAndPriceAndDegree(type, priceFrom, priceTo, degreeFrom, degreeTo,pageable);
        }
    }

    @ResponseBody
    @GetMapping("/board/search")
    public Page<AlcoholResponseDto> searchByName (@RequestParam String name, Pageable p) {

        Pageable pageable = PageRequest.of(p.getPageNumber(),p.getPageSize(), Sort.by("name")); //가나다 순 정렬

        return  alcoholService.searchByName(name, pageable);

    }

    //술 상세페이지
    @ResponseBody
    @GetMapping("/board/{a_id}")
    public Map<String,Object> getBoardDetail (@PathVariable Long a_id, HttpServletRequest request, Pageable pageable) {
        Map<String, Object> res = new HashMap<>(); //response를 위한 map

        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        boolean isZzimed = false; //찜한 술인지 아닌지 판별하기 위한 변수

        if (jwtToken!= null) { //로그인한 유저라면
            Long u_id = authService.getMemberId(jwtToken);


            isZzimed = zzimService.findZzim(u_id, a_id); //찜한 술인지

            alcoholService.printLog(u_id, a_id); //술 상세페이지를 방문했으므로 로그를 찍음

        }

        Optional<VisitDto> visitInfo = visitService.getVisitInfo(a_id); // 방문자 통계정보 가져오기
        AlcoholDetailResponseDto alcoholDetail = alcoholService.getAlcoholDetail(a_id);//술 객체 가져오기

        double alcoholWeight = alcoholDetail.getDegree() * alcoholDetail.getCapacity() * 0.8; //이 술에 들어있는 알코올 중량(g)
        double alcoholPerSoju = Math.round((alcoholWeight/ALCOHOL_IN_SOJU*100)/100.0); //소주 한병으로 환산한 알코올 량(반올림)
        //System.out.println(alcoholPerSoju);

        res.put("alcoholDetail", alcoholDetail); //술 객체정보
        res.put("zzim",isZzimed ); //사용자의 찜여부
        res.put("visit",visitInfo); //방문자 통계 정보
        res.put("alcoholPerSoju", alcoholPerSoju); //소주 한병으로 환산한 알코올 량
        return res;
    }

    @ResponseBody
    @PostMapping("/board/{a_id}") //찜하기 기능
    public HttpStatus saveZzim (@PathVariable Long a_id, HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

//        System.out.println("Jwt token:"+jwtToken);
//        AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
//        String userEmail =authToken.findTokentoEmail();
        //현재 로그인한 유저의 이메일

        User user = userRepository.findById(u_id).orElseThrow();
        HttpStatus httpStatus = zzimService.addZzim(user, a_id);

        return httpStatus; // OK or ALREADY_REPORTED
    }

    @ResponseBody
    @DeleteMapping("/board/{a_id}") //찜삭제
    public HttpStatus deleteZzim (@PathVariable Long a_id, HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

        return zzimService.deleteMyZzim(u_id,a_id);

    }


}
