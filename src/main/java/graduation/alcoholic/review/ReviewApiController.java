package graduation.alcoholic.review;

import graduation.alcoholic.login.domain.auth.jwt.JwtHeaderUtil;
import graduation.alcoholic.login.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewApiController{

    private final ReviewService reviewService;
    private final AuthService authService;

    @PostMapping(value = "/review")
    public Long save(HttpServletRequest httpRequest,
                     @RequestPart("requestDto") ReviewSaveRequestDto requestDto, @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        String jwtToken= JwtHeaderUtil.getAccessToken(httpRequest);
        return reviewService.save(authService.getMemberId(jwtToken), requestDto, fileList);
    }


    @PutMapping("/review/{id}")
    public Long update(@PathVariable Long id, @RequestPart("requestDto") ReviewUpdateRequestDto requestDto,  @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) {

        return reviewService.update(id, requestDto, fileList);
    }



    @GetMapping("/review/alcohol/{alcohol_id}")
    public ReviewTotalResponseDto findByAlcohol(@PathVariable Long alcohol_id) {
        return reviewService.findByAlcohol(alcohol_id);
    }


    @GetMapping("/review/user")
    public List<ReviewResponseDto> findByUser(HttpServletRequest httpRequest) {
        String jwtToken= JwtHeaderUtil.getAccessToken(httpRequest);
        return reviewService.findByUser(authService.getMemberId(jwtToken));
    }


    @DeleteMapping("/review/{id}")
    public Long delete(@PathVariable Long id) {

        reviewService.delete(id);
        return id;
    }

}
