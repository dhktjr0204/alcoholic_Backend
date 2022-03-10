package graduation.alcoholic.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class ReviewApiController{

    private final ReviewService reviewService;


    //성공
    @PostMapping(value = "/review")
    public Long save(@RequestPart("requestDto") ReviewSaveRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        return reviewService.save(requestDto, fileList);
    }

    @PutMapping("/review/{id}")
    public Long update(@PathVariable Long id, @RequestPart ReviewUpdateRequestDto requestDto,  @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        return reviewService.update(id, requestDto, fileList);
    }

    //정렬하기, 에러처리
//    @GetMapping("/review/alcohol/{alcohol_id}")
//    public List<ReviewResponseDto> findByAlcohol(@PathVariable Long alcohol_id, @PageableDefault(size = 5)Pageable pageable) {
//        return reviewService.findByAlcohol(alcohol_id, pageable);
//    }

    @GetMapping("/review/alcohol/{alcohol_id")
    public Map<ReviewTotalResponseDto, List<ReviewResponseDto>> findByAlcohol(@PathVariable Long alcohol_id, @PageableDefault(size = 5)Pageable pageable) {
        return reviewService.findByAlcohol(alcohol_id, pageable);
    }


    //정렬하기, 에러처리
    @GetMapping("/review/user/{user_id}")
    public List<ReviewResponseDto> findByUser(@PathVariable Long user_id, @PageableDefault(size = 5)Pageable pageable) {
        return reviewService.findByUser(user_id, pageable);
    }


    //성공!!!!
    @DeleteMapping("/review/{id}")
    public Long delete(@PathVariable Long id) {

        reviewService.delete(id);
        return id;
    }

}
