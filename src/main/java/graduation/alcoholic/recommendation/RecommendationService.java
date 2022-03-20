package graduation.alcoholic.recommendation;

import graduation.alcoholic.alcohol.AlcoholRepository;
import graduation.alcoholic.domain.enums.Taste;
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

        int taste1 = tasteToInt(responseDto.getTaste1());
        int taste2 = tasteToInt(responseDto.getTaste2());
        int taste3 = tasteToInt(responseDto.getTaste3());
        int taste4 = tasteToInt(responseDto.getTaste4());
        int taste5 = tasteToInt(responseDto.getTaste5());

        int[] array = {taste1, taste2, taste3, taste4, taste5};
        return array;

    }

    public List<RecommendScore> getScore(RecommendRequestDto requestDto, List<AlcoholTasteResponseDto> alcoholTasteResponseDtoList) {

        List<RecommendScore> recommendScoreList = new ArrayList<>();
        Iterator<AlcoholTasteResponseDto> iterator = alcoholTasteResponseDtoList.iterator();

        while(iterator.hasNext()) {

            AlcoholTasteResponseDto tastes = iterator.next();

            int score1 = Math.abs(tasteToInt(requestDto.getTaste1()) - tasteToInt(tastes.getTaste1()));
            int score2 = Math.abs(tasteToInt(requestDto.getTaste2()) - tasteToInt(tastes.getTaste2()));
            int score3 = Math.abs(tasteToInt(requestDto.getTaste3()) - tasteToInt(tastes.getTaste3()));
            int score4 = Math.abs(tasteToInt(requestDto.getTaste4()) - tasteToInt(tastes.getTaste4()));
            int score5 = Math.abs(tasteToInt(requestDto.getTaste5()) - tasteToInt(tastes.getTaste5()));
            int total_score = score1 + score2 + score3 + score4 + score5;

            RecommendScore score = RecommendScore.builder()
                    .id(tastes.getId())
                    .score1(score1)
                    .score2(score2)
                    .score3(score3)
                    .score4(score4)
                    .score5(score5)
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

        return recommendScoreList;


    }




}
