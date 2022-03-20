package graduation.alcoholic.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecommendationApiController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommendation")
    public List<AlcoholResponseDto> getRecommendation(@RequestBody RecommendRequestDto requestDto) {

        return recommendationService.getRecommendation(requestDto);
    }
}
