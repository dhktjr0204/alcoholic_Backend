package graduation.alcoholic.board;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import lombok.RequiredArgsConstructor;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Log4j
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @ResponseBody
    @GetMapping("/board")
    public Optional<List<Alcohol>> getBoard (@RequestParam(required = false) String type,
                                             @RequestParam(required = false) Double degreeFrom, @RequestParam(required = false) Double degreeTo,
                                             @RequestParam(required = false) Integer priceFrom, @RequestParam(required = false) Integer priceTo
                                             ) {


        if (type.equals("전체")) {
          return Optional.ofNullable(boardService.findByPriceAndDegree(priceFrom, priceTo, degreeFrom, degreeTo));
        }

        //주종, 가격대, 도수에 의한 검색
        else {
            return Optional.ofNullable(boardService.findByTypeAndPriceAndDegree(
                    type, priceFrom, priceTo, degreeFrom, degreeTo));
        }
    }

    @ResponseBody
    @GetMapping("/board/search")
    public Optional<List<Alcohol>> searchByName (@RequestParam String name, Pageable pageable) {
        List<Alcohol> res = boardRepository.findByNameContains(name);
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
    public String addZzim (@PathVariable Long a_id, @AuthenticationPrincipal CustomUserDetails user) {

        System.out.println();
        if (user==null) {
            //로그인페이지로 이동
        }
        else {
         //   boardService.addZzim(user, a_id);
            System.out.println("찜이되엇습니다");
        }
        return "찜";
    }
}
