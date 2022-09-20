package graduation.alcoholic.web.bar;

import graduation.alcoholic.web.bar.dto.BarResponseDto;
import graduation.alcoholic.web.bar.dto.BarSaveRequestDto;
import graduation.alcoholic.web.bar.dto.BarUpdateRequestDto;
import graduation.alcoholic.web.login.AuthService;
import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BarController {
    private final BarService barService;
    private final AuthService authService;
    //모든 정보 가져오기
    @GetMapping("/bar")
    public Page<BarResponseDto> getBar(@PageableDefault(size = 10) Pageable pageable){
        return barService.getBars(pageable);
    }

    //마이페이지에서 조회
    @GetMapping("/mypage/bar")
    public Page<BarResponseDto> getMyBar(HttpServletRequest httpServletRequest,@PageableDefault(size = 10) Pageable pageable){
        String jwtToken = JwtHeaderUtil.getAccessToken(httpServletRequest);
        return barService.getMyBars(authService.getMemberId(jwtToken),pageable);
    }

    //추가하기
    @PostMapping("/bar")
    public ResponseEntity<Map<String,Long>> saveBar(HttpServletRequest httpServletRequest, @RequestPart("barSaveDto") BarSaveRequestDto barSaveDTO, @RequestPart(value = "fileList") List<MultipartFile> fileList){
        String jwtToken = JwtHeaderUtil.getAccessToken(httpServletRequest);

        return barService.saveBar(authService.getMemberId(jwtToken),barSaveDTO, fileList);
    }

    //상세페이지
    @GetMapping("/bar/{id}")
    public List<BarResponseDto> getBarDetail (@PathVariable Long id) {
        return barService.getBarDetail(id);
    }


    //업데이트
    @PutMapping("/bar/{id}")
    public ResponseEntity<Map<String, Boolean>> updateBar(HttpServletRequest request, @PathVariable Long id, @RequestPart("barUpdateDto") BarUpdateRequestDto requestDto, @RequestPart(value = "fileList",required = false) List<MultipartFile> fileList){
        return barService.updateBar(request, id, requestDto, fileList);
    }

    //삭제하기
    @DeleteMapping("/bar/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBar(HttpServletRequest request,@PathVariable Long id){
        return barService.deleteBar(request,id);
    }


    //검색기능
    @GetMapping("/bar/search")
    public Optional<Page<BarResponseDto>> searchByContents(@RequestParam(value = "location",required = false) String location,
                                                           @RequestParam(value = "contents",required = false) String contents,
                                                           @PageableDefault(size = 10) Pageable pageable){
        return Optional.of(barService.searchByContents(location,contents,pageable));
    }
}

