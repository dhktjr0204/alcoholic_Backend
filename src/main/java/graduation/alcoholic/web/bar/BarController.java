package graduation.alcoholic.web.bar;

import graduation.alcoholic.web.bar.dto.BarResponseDto;
import graduation.alcoholic.web.bar.dto.BarSaveDto;
import graduation.alcoholic.web.bar.dto.BarUpdateRequestDto;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
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
    public Page<BarResponseDto> listAllBar(@PageableDefault(size = 20) Pageable pageable){
        return barService.listAllBars(pageable);
    }

    //추가하기
    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<Map<String,Long>> addBarInfo(@RequestPart("barSaveDto") BarSaveDto barSaveDTO, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList){
        return barService.createBar(barSaveDTO, fileList);
    }

    //상세페이지
    @ResponseBody
    @GetMapping("/{id}")
    public List<BarResponseDto> getBarDetail (@PathVariable Long id) {
        return barService.getBarDetail(id);
    }

    //업데이트
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateBar(@PathVariable Long id, @RequestPart("barUpdateDto") BarUpdateRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList){
        return barService.updateBar(id, requestDto, fileList);
    }

    //삭제하기
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>>  deleteBar(@PathVariable Long id){
        return barService.deleteBar(id);
    }


    //검색기능
    @ResponseBody
    @GetMapping("/search")
    public Optional<Page<BarResponseDto>> searchBar(@RequestParam("title") String title, @PageableDefault(size = 20) Pageable pageable){
        return Optional.of(barService.searchBar(title,pageable));
    }

    //지역별 찾기
    @ResponseBody
    @GetMapping("/local")
    public Optional<Page<BarResponseDto>> LocalBar(@RequestParam("location") String location, @PageableDefault(size = 20) Pageable pageable){
        return Optional.of(barService.localBar(location,pageable));
    }

}

