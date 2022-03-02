package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
    public List<Bar> listAllBar(){
        return barService.listAllBars();
    }


    //추가하기
    @ResponseBody
    @PostMapping("/add")
    public Bar addBarInfo(@RequestParam Long id,@RequestBody BarDTO barDTO){
        return barService.createBar(id, barDTO);
    }


    //상세페이지
    @ResponseBody
    @GetMapping("/bar/{id}")
    public Map<String,Object> getBarDetail (@PathVariable Long id) {
        Optional<Bar> barDetail = barService.getBarDetail(id);//술 객체 가져오기
        Map<String, Object> res = new HashMap<>();
        res.put("barDetail", barDetail);
        return res;
    }


    @ResponseBody
    @PutMapping("/bar/{id}")
    public ResponseEntity<Bar> updateBar(@Param("id") Long id, @RequestBody BarDTO barDetail){
        return barService.updateBar(id, barDetail);
    }

    @DeleteMapping("bar/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteBar(@PathVariable Long id){
        return barService.deleteBar(id);
    }

}

