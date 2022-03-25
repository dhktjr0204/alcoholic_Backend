package graduation.alcoholic.web.bar;

import graduation.alcoholic.web.bar.dto.BarResponseDto;
import graduation.alcoholic.web.bar.dto.BarSaveRequestDto;
import graduation.alcoholic.web.bar.dto.BarUpdateRequestDto;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bar")
public class BarController {
    private final BarService barService;

    //모든 정보 가져오기
    @GetMapping("/")
    public Page<BarResponseDto> getBar(@PageableDefault(size = 10) Pageable pageable){
        return barService.getBars(pageable);
    }

    //추가하기
    @PostMapping("/")
    public ResponseEntity<Map<String,Long>> saveBar(@RequestPart("barSaveDto") BarSaveRequestDto barSaveDTO, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList){
        return barService.saveBar(barSaveDTO, fileList);
    }

    //상세페이지
    @GetMapping("/{id}")
    public List<BarResponseDto> getBarDetail (@PathVariable Long id) {
        return barService.getBarDetail(id);
    }


    //업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> updateBar(@PathVariable Long id, @RequestPart("barUpdateDto") BarUpdateRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList){
        return barService.updateBar(id, requestDto, fileList);
    }

    //삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBar(@PathVariable Long id){
        return barService.deleteBar(id);
    }


    //검색기능
    @GetMapping("/search")
    public Optional<Page<BarResponseDto>> searchByTitle(@RequestParam("title") String title, @PageableDefault(size = 10) Pageable pageable){
        return Optional.of(barService.searchByTitle(title,pageable));
    }

    //지역별 찾기
    @GetMapping("/search")
    public Optional<Page<BarResponseDto>> searchByLocation(@RequestParam("location") String location, @PageableDefault(size = 10) Pageable pageable){
        return Optional.of(barService.searchByLocation(location,pageable));
    }

}

