package graduation.alcoholic.web.board.alcohol;

import graduation.alcoholic.web.board.alcohol.dto.AlcoholDetailResponseDto;
import graduation.alcoholic.web.board.alcohol.dto.AlcoholResponseDto;
import graduation.alcoholic.web.login.AuthService;
import graduation.alcoholic.web.mypage.zzim.ZzimService;
import graduation.alcoholic.web.board.visit.VisitAnalysisService;
import graduation.alcoholic.web.board.visit.dto.VisitResponseDto;
import graduation.alcoholic.domain.entity.User;
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

@RestController
@RequiredArgsConstructor
public class AlcoholController {


    private final AlcoholService alcoholService;
    private final UserRepository userRepository;
    private final ZzimService zzimService;
    private final VisitAnalysisService visitService;
    private final AuthService authService;


    @GetMapping("/board")
    public Page<AlcoholResponseDto> getBoard (String type, Double degreeFrom, Double degreeTo,
                                              Integer priceFrom, Integer priceTo, @PageableDefault(size = 12) Pageable p) {
        Pageable pageable = PageRequest.of(p.getPageNumber(),p.getPageSize(), Sort.by("name")); // 가나다순 정렬
        if (type.equals("전체")) {
            if (degreeFrom==0 && degreeTo==30 && priceFrom==0 && priceTo==100000) {
                return alcoholService.getAllAlcohols(pageable);
            }
            else
                return alcoholService.findAlcoholByPriceAndDegree(priceFrom, priceTo, degreeFrom, degreeTo,pageable);
        }

        else {
            return alcoholService.findAlcoholByTypeAndPriceAndDegree(type, priceFrom, priceTo, degreeFrom, degreeTo,pageable);
        }
    }

    @GetMapping("/board/search")
    public List<AlcoholResponseDto> searchAlcoholByName (@RequestParam String name) {
    //no pagination
        return  alcoholService.searchByName(name);
    }

    //술 상세페이지
    @GetMapping("/board/{a_id}")
    public Map<String,Object> getBoardDetail (@PathVariable Long a_id, HttpServletRequest request, Pageable pageable) {
        Map<String, Object> res = new HashMap<>(); //response를 위한 map

        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        boolean isZzimed = false; //찜한 술인지 아닌지 판별하기 위한 변수

        Long u_id = authService.getMemberId(jwtToken);

        if (u_id!= null) { //로그인한 유저라면
            isZzimed = zzimService.findZzim(u_id, a_id); //찜한 술인지
            alcoholService.printLog(u_id, a_id); //술 상세페이지를 방문했으므로 로그를 찍음
        }

        VisitResponseDto visitInfo = visitService.getVisitInfo(a_id); // 방문자 통계정보 가져오기
        AlcoholDetailResponseDto alcoholDetail = alcoholService.getAlcoholDetail(a_id);//술 객체 가져오기
        Double alcoholPerSoju = alcoholService.getAlcoholPerSoju(alcoholDetail); //소주한병으로 환산한 알코올량 가져오기

        res.put("alcoholDetail", alcoholDetail); //술 객체정보
        res.put("zzim",isZzimed ); //사용자의 찜여부
        res.put("visit",visitInfo); //방문자 통계 정보
        res.put("alcoholPerSoju", alcoholPerSoju); //소주 한병으로 환산한 알코올 량
        System.out.println(alcoholPerSoju);

        return res;
    }

    @PostMapping("/board/{a_id}") //찜하기 기능
    public HttpStatus saveZzim (@PathVariable Long a_id, HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

        User user = userRepository.findById(u_id).orElseThrow();
        HttpStatus httpStatus = zzimService.addZzim(user, a_id);

        return httpStatus; // OK or ALREADY_REPORTED
    }

    @DeleteMapping("/board/{a_id}") //찜삭제
    public HttpStatus deleteZzim (@PathVariable Long a_id, HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        Long u_id = authService.getMemberId(jwtToken);

        return zzimService.deleteMyZzim(u_id,a_id);

    }


}
