package graduation.alcoholic.web.review;

import graduation.alcoholic.web.login.domain.jwt.JwtHeaderUtil;
import graduation.alcoholic.web.login.AuthService;
import graduation.alcoholic.web.review.dto.ReviewResponseDto;
import graduation.alcoholic.web.review.dto.ReviewSaveRequestDto;
import graduation.alcoholic.web.review.dto.ReviewTotalResponseDto;
import graduation.alcoholic.web.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    private final AuthService authService;



    @PostMapping(value = "/review")
    public Long save(HttpServletRequest httpRequest,
                     @RequestPart(value = "requestDto") ReviewSaveRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        if (fileList == null)
            System.out.println("fileList controller에서도 null");
        String jwtToken = JwtHeaderUtil.getAccessToken(httpRequest);
        return reviewService.save(authService.getMemberId(jwtToken), requestDto, fileList);

    }

    @PutMapping("/review/{id}")
    public Long update(@PathVariable Long id, @RequestPart("requestDto") ReviewUpdateRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        return reviewService.update(id, requestDto, fileList);
    }



    @GetMapping("/review/alcohol/{alcohol_id}")
    public ReviewTotalResponseDto findByAlcohol(@PathVariable Long alcohol_id) {
        return reviewService.findByAlcohol(alcohol_id);
    }


    @GetMapping("/review/user")
    public List<ReviewResponseDto> findByUser(HttpServletRequest httpRequest){
            String jwtToken = JwtHeaderUtil.getAccessToken(httpRequest);
            return reviewService.findByUser(authService.getMemberId(jwtToken));

    }


    @DeleteMapping("/review/{id}")
    public Long delete(@PathVariable Long id) {

        reviewService.delete(id);
        return id;
    }

}
