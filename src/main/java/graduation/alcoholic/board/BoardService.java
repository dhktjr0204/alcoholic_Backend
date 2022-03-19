package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class BoardService {
    private final BoardRepository boardRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Page<BoardResponseDto> searchByName (String name, Pageable pageable) {
        return boardRepository.findByNameContains(name,pageable).map(alcohol -> new BoardResponseDto(alcohol));
    }

    public BoardDetailResponseDto getAlcoholDetail(Long id) {
        Alcohol alcoholDetail = boardRepository.findById(id).orElseThrow();
        return new BoardDetailResponseDto(alcoholDetail);
    }

    public void printLog (Long u_id, Long a_id) {
        logger.info("u_id: "+u_id+" a_id: "+a_id);
    }


    public  Page<BoardResponseDto> findByPriceAndDegree (
            Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable
    ) {
        Page<Alcohol> resultEntity ;
        if (degreeTo == 30 && priceTo == 100000) {
            //30도 이상, 10만원이상 선택시
            resultEntity=boardRepository.findByDegreeGreaterThanAndPriceGreaterThan(degreeFrom, priceFrom, pageable);
        } else if (degreeTo == 30) {
            //30도 이상만 선택시
            resultEntity =boardRepository.findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(priceFrom, priceTo, degreeFrom, pageable);
        } else if (priceTo == 100000) {
            //10만원 이상만 선택시
            resultEntity = boardRepository.findByDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(degreeFrom, degreeTo, priceFrom, pageable);
        } else {
            //그외
            resultEntity =boardRepository.findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
        return resultEntity.map(alcohol -> new BoardResponseDto(alcohol));
    }

    public Page<BoardResponseDto> findByTypeAndPriceAndDegree (
            String type, Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable
            ) {

        Type enumType = Type.valueOf(type);
        Page<Alcohol> resultEntity ;
        if (degreeTo==30 && priceTo== 100000) {
            //30도 이상, 10만원이상 선택시
            resultEntity= boardRepository.findByTypeAndDegreeGreaterThanAndPriceGreaterThan(enumType,degreeFrom, priceFrom, pageable);
        }
        else if (degreeTo==30) {
            //30도 이상만 선택시
            resultEntity= boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(enumType, priceFrom, priceTo, degreeFrom, pageable);
        }
        else if (priceTo==100000) {
            //10만원 이상만 선택시

            resultEntity= boardRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(enumType, degreeFrom, degreeTo, priceFrom,pageable);
        }
        else {
            //그외
            resultEntity= boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    enumType, priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
        return resultEntity.map(alcohol -> new BoardResponseDto(alcohol));
    }




}
