package graduation.alcoholic.board;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.enums.Type;
import lombok.RequiredArgsConstructor;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @ResponseBody
    @GetMapping("/board")
    public Optional<Page<Alcohol>> getBoard (@RequestParam(required = false) String type,
                                             @RequestParam(required = false) Double degreeFrom, @RequestParam(required = false) Double degreeTo,
                                             @RequestParam(required = false) Integer priceFrom, @RequestParam(required = false) Integer priceTo,
                                             @PageableDefault(size = 12) Pageable pageable
                                             ) {

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
    public Optional<Page<Alcohol>> searchByName (@RequestParam String name, Pageable pageable) {
        Page<Alcohol> res = boardRepository.findByNameContains(name, pageable);
        return Optional.of(res);
    }

    //상세페이지
    @ResponseBody
    @GetMapping("/board/{id}")
    public Map<String,Object> getBoardDetail (@PathVariable Long id) {
        Optional<Alcohol> alcoholDetail = boardService.getAlcoholDetail(id);//술 객체 가져오기
        // 리뷰 가져오기
        // 통계 가져오기

        Map<String, Object> res = new HashMap<>();
        res.put("alcoholDetail", alcoholDetail);
        //detail.put();
        //detail.put();

        return res;
    }

    @ResponseBody
    @PostMapping("/board/{a_id}") //찜하기 기능
    public String addZzim (@PathVariable Long a_id, HttpSession session) {
        System.out.println(session.getId());
       // System.out.println(principal.getName());
//        if (principal==null) {
//            //로그인페이지로 이동
//            System.out.println("유저가 없음");
//        }

            User user = new User("정", "dhktjr0204@naver.com", "female", "20~29", null);
            boardService.addZzim(user, a_id);
            System.out.println("찜이되엇습니다");

        return "찜";
    }


}
