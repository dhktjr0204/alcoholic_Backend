package graduation.alcoholic.recommendation;

import graduation.alcoholic.alcohol.AlcoholRepository;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Taste;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static graduation.alcoholic.domain.enums.Type.증류주;


@RequiredArgsConstructor
@Service
public class RecommendationService {

    private final AlcoholRepository alcoholRepository;
    private final AlcoholTasteRepository alcoholTasteRepository;


    @Transactional(readOnly = true)
    public List<AlcoholResponseDto> getRecommendation(RecommendRequestDto requestDto) {

        List<AlcoholResponseDto> alcoholList = new ArrayList<>();
        List<AlcoholTasteResponseDto> alcoholTasteList;

        if (requestDto.getType() == 증류주) {
            switch (requestDto.getDegree()) {
                case "low":
                     alcoholTasteList = alcoholTasteRepository.findSojuDegreeLessThanequal25().stream()
                            .map(AlcoholTasteResponseDto::new)
                            .collect(Collectors.toList());
                     break;
                case "high":
                    alcoholTasteList = alcoholTasteRepository.findSojuDegreeGreaterThan25().stream()
                            .map(AlcoholTasteResponseDto::new)
                            .collect(Collectors.toList());
                    break;
                default:
                    throw new IllegalArgumentException("잘못된 degree 요청값 degree: " + requestDto.getDegree());
            }
        }

        else  {
            alcoholTasteList = alcoholTasteRepository.findByType(requestDto.getType()).stream()
                    .map(AlcoholTasteResponseDto::new)
                    .collect(Collectors.toList());
        }


        List<RecommendScore> recommendScoreList = getScore(requestDto, alcoholTasteList);

        recommendScoreList.removeIf(recommendScore -> (recommendScore.getScore1() > 1) || (recommendScore.getScore2() > 1) || (recommendScore.getScore3() > 1) || (recommendScore.getScore4() > 1) || (recommendScore.getScore5() > 1));

        if (!recommendScoreList.isEmpty()) {
            recommendScoreList.sort(Comparator.comparing(RecommendScore::getTotal_score));

            for (int i=0; i<5; i++) {
                if (i >= recommendScoreList.size())
                    break;
                else {
                    RecommendScore recommendScore = recommendScoreList.get(i);
                    Alcohol alcohol = alcoholRepository.findById(recommendScore.getId()).orElseThrow();
                    alcoholList.add(new AlcoholResponseDto(alcohol));
                }
            }
            printScoreList(recommendScoreList);
        }

        return alcoholList;
    }


    public void printScoreList(List<RecommendScore> recommendScoreList) {
        for (RecommendScore recommendScore : recommendScoreList) {
            System.out.println(recommendScore.getId() + " " +
                    recommendScore.getScore1() + " " + recommendScore.getScore2() + " " + recommendScore.getScore3() + " " + recommendScore.getScore4() + " " + recommendScore.getScore5() + " " +
                    recommendScore.getTotal_score());

        }
    }


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

}
