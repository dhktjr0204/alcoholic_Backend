package graduation.alcoholic.recommend;

import graduation.alcoholic.domain.Alcohol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {
    private final RecommendService recommendService;
    @ResponseBody
    @GetMapping("/type")
    public Map<String, RecommendTypeDto> typeTaste(@RequestParam("type") String type){
        return recommendService.returnType(type);
    }

    @ResponseBody
    @GetMapping("/select")
    public Map<String,Alcohol> Select(@RequestPart("user") RecommendDto recommendDto){
        System.out.println(recommendDto.getType());
        System.out.println(recommendDto.getStart_degree());
        System.out.println(recommendDto.getEnd_degree());
        System.out.println(recommendDto.getStart_price());
        System.out.println(recommendDto.getEnd_price());
        System.out.println(recommendDto.getTaste_1());
        System.out.println(recommendDto.getTaste_2());

        Map<String, Alcohol> res=new HashMap<>();
        List<Alcohol> result=recommendService.recommendAlcohol(recommendDto);
        res.put("첫번째 술",result.get(0));
        res.put("두번째 술",result.get(1));
        res.put("세번째 술",result.get(2));

        return res;
    }
}
