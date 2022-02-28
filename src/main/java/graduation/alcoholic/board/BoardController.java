package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @ResponseBody
    @GetMapping("/board")
    public Optional<List<Alcohol>> getBoard (@RequestParam(required = false) String type,
                                             @RequestParam(required = false) Double degreeFrom, @RequestParam(required = false) Double degreeTo,
                                             @RequestParam(required = false) Long priceFrom, @RequestParam(required = false) Long priceTo,
                                             @PageableDefault(size = 12) Pageable pageable) {

        //그냥 처음들어왔을때 , 검색 X일때
        if (type == null && degreeFrom == null && priceFrom == null && degreeTo == null && priceTo == null) {
            Page<Alcohol> entities = boardRepository.findAll(pageable);
            return Optional.of(entities.getContent());
        }

        //주종, 가격대, 도수에 의한 검색
        else {
            return Optional.ofNullable(boardService.findByTypeAndPriceAndDegree(
                    type, priceFrom, priceTo, degreeFrom, degreeTo, pageable));

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

    @PostMapping("/board/{id}") //찜하기 기능
    public void addZzim (@PathVariable Long a_id) {

    }
}
