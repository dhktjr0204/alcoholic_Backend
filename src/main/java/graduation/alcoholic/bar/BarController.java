package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bar")
public class BarController {

    @Autowired
    private final BarService barService;

    //모든 정보 가져오기
    @ResponseBody
    @GetMapping("/board")
    public Optional<List<Bar>> listAllBar(@PageableDefault(size = 12) Pageable pageable){
        return barService.listAllBars(pageable);
    }


    //추가하기
    @ResponseBody
    @PostMapping("/add")
    public Bar addBarInfo(@RequestParam("id") Long userId,@RequestBody BarDTO barDTO){
        return barService.createBar(userId, barDTO);
    }


    //상세페이지
    @ResponseBody
    @GetMapping("/{id}")
    public Map<String,Object> getBarDetail (@PathVariable Long id) {
        Optional<Bar> barDetail = barService.getBarDetail(id);
        Map<String, Object> res = new HashMap<>();
        res.put("barDetail", barDetail);
        return res;
    }


    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateBar(@PathVariable Long id, @RequestBody BarUpdateDTO barDetail){
        return barService.updateBar(id, barDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteBar(@PathVariable Long id){
        return barService.deleteBar(id);
    }

}

