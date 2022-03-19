package graduation.alcoholic.recommendation;

import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import graduation.alcoholic.review.domain.AlcoholRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class RecommendationService {

    private final AlcoholRepository alcoholRepository;
    private final AlcoholTasteRepository alcoholTasteRepository;

    public int tasteToInt(Taste taste) {

        switch (taste) {
            case 없음:
                return 0;
            case 약함:
                return 1;
            case 보통:
                return 2;
            case 강함:
                return 3;
            default:
                return -1;

        }
    }

    public int[] dtoToArray(AlcoholTasteResponseDto responseDto) {

        int taste_1 = tasteToInt(responseDto.getTaste_1());
        int taste_2 = tasteToInt(responseDto.getTaste_2());
        int taste_3 = tasteToInt(responseDto.getTaste_3());
        int taste_4 = tasteToInt(responseDto.getTaste_4());
        int taste_5 = tasteToInt(responseDto.getTaste_5());

        int[] array = {taste_1, taste_2, taste_3, taste_4, taste_5};
        return array;

    }

    public List<RecommendScore> getScore(RecommendRequestDto requestDto, List<AlcoholTasteResponseDto> alcoholTasteResponseDtoList) {

        List<RecommendScore> recommendScoreList = new ArrayList<>();
        Iterator<AlcoholTasteResponseDto> iterator = alcoholTasteResponseDtoList.iterator();

        while(iterator.hasNext()) {

            AlcoholTasteResponseDto tastes = iterator.next();

            int score_1 = Math.abs(tasteToInt(requestDto.getTaste_1()) - tasteToInt(tastes.getTaste_1()));
            int score_2 = Math.abs(tasteToInt(requestDto.getTaste_2()) - tasteToInt(tastes.getTaste_2()));
            int score_3 = Math.abs(tasteToInt(requestDto.getTaste_3()) - tasteToInt(tastes.getTaste_3()));
            int score_4 = Math.abs(tasteToInt(requestDto.getTaste_4()) - tasteToInt(tastes.getTaste_4()));
            int score_5 = Math.abs(tasteToInt(requestDto.getTaste_5()) - tasteToInt(tastes.getTaste_5()));
            int total_score = score_1 + score_2 + score_3 + score_4 + score_5;

            RecommendScore score = RecommendScore.builder()
                    .id(tastes.getId())
                    .score_1(score_1)
                    .score_2(score_2)
                    .score_3(score_3)
                    .score_4(score_4)
                    .score_5(score_5)
                    .total_score(total_score)
                    .build();
            recommendScoreList.add(score);
        }

        return recommendScoreList;
    }

    @Transactional(readOnly = true)
    public List<RecommendScore> getRecommendation(RecommendRequestDto requestDto) {


        List<AlcoholTasteResponseDto> alcoholTasteList = alcoholTasteRepository.findByType(requestDto.getType()).stream()
                .map(AlcoholTasteResponseDto::new)
                .collect(Collectors.toList());


        List<RecommendScore> recommendScoreList = getScore(requestDto, alcoholTasteList);
        recommendScoreList.sort(Comparator.comparing(RecommendScore::getTotal_score).thenComparing(RecommendScore::getStd));
        Collections.sort(recommendScoreList);

        return recommendScoreList;


    }




}
