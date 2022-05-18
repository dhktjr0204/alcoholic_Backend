package graduation.alcoholic.web.recommendation;

import graduation.alcoholic.domain.repository.AlcoholRepository;
import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.repository.AlcoholTasteRepository;
import graduation.alcoholic.domain.repository.board.alcohol.dto.AlcoholDetailResponseDto;
import graduation.alcoholic.web.recommendation.dto.AlcoholTasteResponseDto;
import graduation.alcoholic.web.recommendation.dto.RecommendRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static graduation.alcoholic.domain.enums.Type.증류주;
import static java.util.Comparator.comparing;


@RequiredArgsConstructor
@Service
public class RecommendationService {

    private final AlcoholRepository alcoholRepository;
    private final AlcoholTasteRepository alcoholTasteRepository;


    @Transactional(readOnly = true)
    public List<AlcoholDetailResponseDto> getRecommendation(RecommendRequestDto requestDto) {

        List<AlcoholDetailResponseDto> alcoholList = new ArrayList<>();
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

        List<Integer> noneList = getTasteCheckList(requestDto, Taste.없음);
        List<Integer> strongList = getTasteCheckList(requestDto, Taste.강함);


        recommendScoreList.removeIf(recommendScore -> (recommendScore.getScore1() > 2) || (recommendScore.getScore2() > 2) || (recommendScore.getScore3() > 2) || (recommendScore.getScore4() > 2) || (recommendScore.getScore5() > 2));

        if (!recommendScoreList.isEmpty()) {

                countNoneScore(recommendScoreList, noneList);
                countStrongScore(recommendScoreList, strongList);

                recommendScoreList.sort(comparing(RecommendScore::getTotal_score).thenComparing(RecommendScore::getStd).thenComparing(comparing(RecommendScore::getNone_score).reversed()).thenComparing(comparing(RecommendScore::getStrong_score).reversed()));

        }

        for (int i=0; i<5; i++) {
            if (i >= recommendScoreList.size())
                break;
            else {
                RecommendScore recommendScore = recommendScoreList.get(i);
                Alcohol alcohol = alcoholRepository.findById(recommendScore.getId()).orElseThrow();
                alcoholList.add(new AlcoholDetailResponseDto(alcohol));
            }
        }
        printScoreList(recommendScoreList);

        return alcoholList;
    }


    public void printScoreList(List<RecommendScore> recommendScoreList) {

        for (RecommendScore recommendScore : recommendScoreList) {

            System.out.printf("%-3s %d %d %d %d %d   %d %f %d %d%n",recommendScore.getId(),
                    recommendScore.getScore1(),recommendScore.getScore2() ,recommendScore.getScore3(), recommendScore.getScore4(), recommendScore.getScore5(),
                    recommendScore.getTotal_score(), recommendScore.getStd() ,recommendScore.getNone_score(), recommendScore.getStrong_score());
            System.out.println();

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

    public List<Integer> getTasteCheckList(RecommendRequestDto requestDto, Taste taste) {

        List<Integer> checkList = new ArrayList<>();

        if (requestDto.getTaste1() == Taste.없음)
            checkList.add(1);
        else if (requestDto.getTaste2() == Taste.없음)
            checkList.add(2);
        else if (requestDto.getTaste3() == Taste.없음)
            checkList.add(3);
        else if (requestDto.getTaste4() == Taste.없음)
            checkList.add(4);
        else if (requestDto.getTaste5() == Taste.없음)
            checkList.add(5);

        return checkList;
    }

    public void countNoneScore(List<RecommendScore> recommendScoreList, List<Integer> noneList) {

        for (Integer none: noneList) {
            for (RecommendScore recommendScore : recommendScoreList) {
                switch (none) {
                    case 1:
                        if (recommendScore.getScore1() == 0)
                            recommendScore.setNone_score(recommendScore.getNone_score() + 1);
                        break;
                    case 2:
                        if (recommendScore.getScore2() == 0)
                            recommendScore.setNone_score(recommendScore.getNone_score() + 1);
                        break;
                    case 3:
                        if (recommendScore.getScore3() == 0)
                            recommendScore.setNone_score(recommendScore.getNone_score() + 1);
                        break;
                    case 4:
                        if (recommendScore.getScore4() == 0)
                            recommendScore.setNone_score(recommendScore.getNone_score() + 1);
                        break;
                    case 5:
                        if (recommendScore.getScore5() == 0)
                            recommendScore.setNone_score(recommendScore.getNone_score() + 1);
                        break;
                }
            }
        }
    }


    public void countStrongScore(List<RecommendScore> recommendScoreList, List<Integer> strongList) {

        for (Integer none: strongList) {
            for (RecommendScore recommendScore : recommendScoreList) {
                switch (none) {
                    case 1:
                        if (recommendScore.getScore1() == 0)
                            recommendScore.setStrong_score(recommendScore.getNone_score() + 1);
                        break;
                    case 2:
                        if (recommendScore.getScore2() == 0)
                            recommendScore.setStrong_score(recommendScore.getNone_score() + 1);
                        break;
                    case 3:
                        if (recommendScore.getScore3() == 0)
                            recommendScore.setStrong_score(recommendScore.getNone_score() + 1);
                        break;
                    case 4:
                        if (recommendScore.getScore4() == 0)
                            recommendScore.setStrong_score(recommendScore.getNone_score() + 1);
                        break;
                    case 5:
                        if (recommendScore.getScore5() == 0)
                            recommendScore.setStrong_score(recommendScore.getNone_score() + 1);
                        break;
                }
            }
        }
    }

}
