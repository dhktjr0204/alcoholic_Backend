package graduation.alcoholic.web.recommendation;

import graduation.alcoholic.web.board.alcohol.dto.AlcoholDetailResponseDto;
import graduation.alcoholic.web.recommendation.dto.RecommendRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecommendationiController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommendation")
    public List<AlcoholDetailResponseDto> getRecommendation(@RequestBody RecommendRequestDto requestDto) {

        return recommendationService.getRecommendation(requestDto);
    }
}
