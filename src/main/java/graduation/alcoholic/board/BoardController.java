package graduation.alcoholic.board;

import graduation.alcoholic.Mypage.Zzim.ZzimService;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.auth.jwt.AuthToken;
import graduation.alcoholic.login.domain.auth.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.auth.jwt.JwtHeaderUtil;
import graduation.alcoholic.login.domain.member.UserRepository;
import graduation.alcoholic.review.ReviewService;
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
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final UserRepository userRepository;
    private final ReviewService reviewService;
    private final ZzimService zzimService;
    private final AuthTokenProvider authTokenProvider;

    @ResponseBody
    @GetMapping("/board")
    public Optional<Page<Alcohol>> getBoard (String type,
                                             Double degreeFrom,Double degreeTo,
                                              Integer priceFrom,  Integer priceTo,
                                             @PageableDefault(size = 12) Pageable p
                                             ) {
        Pageable pageable = PageRequest.of(p.getPageNumber(),p.getPageSize(), Sort.by("name"));

        if (type.equals("전체")) {
          return Optional.ofNullable(boardService.findByPriceAndDegree(priceFrom, priceTo, degreeFrom, degreeTo,pageable));
        }

        //주종, 가격대, 도수에 의한 검색
        else {
            return Optional.ofNullable(boardService.findByTypeAndPriceAndDegree(
                    type, priceFrom, priceTo, degreeFrom, degreeTo,pageable));
        }
    }

    @ResponseBody
    @GetMapping("/board/search")
    public Optional<Page<Alcohol>> searchByName (@RequestParam String name, Pageable p) {
        Pageable pageable = PageRequest.of(p.getPageNumber(),p.getPageSize(), Sort.by("name"));
        Page<Alcohol> res = boardRepository.findByNameContains(name, pageable);

        return Optional.of(res);
    }

    //상세페이지
    @ResponseBody
    @GetMapping("/board/{a_id}")
    public Map<String,Object> getBoardDetail (@PathVariable Long a_id, HttpServletRequest request, Pageable pageable) {
        Map<String, Object> res = new HashMap<>();

        // 통계 가져오기
        //detail.put();

        Optional<Alcohol> alcoholDetail = boardService.getAlcoholDetail(a_id);//술 객체 가져오기
        res.put("alcoholDetail", alcoholDetail);

        String jwtToken = JwtHeaderUtil.getAccessToken(request);
        boolean isZzimed = false;
        if (jwtToken!= null) {
            AuthToken authToken = authTokenProvider.convertAuthToken(jwtToken);
            String userEmail = authToken.findTokentoEmail();
            Long u_id = userRepository.findByEmail(userEmail).getId();

            isZzimed = zzimService.findZzim(u_id, a_id);
        }

        res.put("zzim",isZzimed ); //사용자의 찜여부

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
