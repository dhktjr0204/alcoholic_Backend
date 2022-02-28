package graduation.alcoholic.Mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class myReviewController {

    @GetMapping("/myReview")
    public void getMyReviewList (Pageable pageable) {
        //return reviewRepository.findByUId();
    }

    @DeleteMapping("/myReview/{r_id}")
    public void deleteMyReview (@PathVariable Long r_id) {
        //reviewRepository.delete(r_id);
    }

}
