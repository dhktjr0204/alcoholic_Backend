package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public List<BarResponseDTO> listAllBar(@PageableDefault(size = 20) Pageable pageable){
        return barService.listAllBars(pageable);
    }


    //추가하기
    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<Map<String,Long>> addBarInfo(@RequestPart("barSaveDto") BarSaveDTO barSaveDTO,@RequestPart(value = "fileList", required = false) List<MultipartFile> fileList){
        return barService.createBar(barSaveDTO, fileList);
    }


    //상세페이지
    @ResponseBody
    @GetMapping("/{id}")
    public List<BarResponseDTO> getBarDetail (@PathVariable Long id) {
        return barService.getBarDetail(id);
    }

    //업데이트
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateBar(@PathVariable Long id, @RequestPart("barUpdateDto") BarUpdateDTO requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList){
        return barService.updateBar(id, requestDto, fileList);
    }

    //삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>>  deleteBar(@PathVariable Long id){
        return barService.deleteBar(id);
    }

}

