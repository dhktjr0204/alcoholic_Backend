package graduation.alcoholic.Mypage;

import graduation.alcoholic.review.ReviewService;
import graduation.alcoholic.review.domain.dtos.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class myReviewController {

    private final ReviewService reviewService;


//    @GetMapping("/myReview")
//    public List<ReviewResponseDto> getMyReviewList (Pageable pageable) {
//        return reviewService.findByUser(1L,pageable); //예시
//    }


    @DeleteMapping("/myReview/{r_id}")
    public void deleteMyReview (@PathVariable Long r_id) {
        reviewService.delete(r_id);
    }

    @PutMapping("/myReview/{r_id}")
    public Long updateReview (@PathVariable Long r_id, ReviewUpdateRequestDto requestDto,
                              @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {
            return reviewService.update(r_id,requestDto, fileList);
    }


}
