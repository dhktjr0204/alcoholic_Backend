package graduation.alcoholic.web.board.alcohol;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import graduation.alcoholic.domain.repository.AlcoholRepository;
import graduation.alcoholic.web.board.alcohol.dto.AlcoholDetailResponseDto;
import graduation.alcoholic.web.board.alcohol.dto.AlcoholResponseDto;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AlcoholService {
    private final AlcoholRepository alcoholRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Double ALCOHOL_IN_SOJU = 16.9*360*0.8; //소주에 들어있는 알코올량 (g) 4867.2 5440

    //술 이름으로 검색
    public Page<AlcoholResponseDto> searchByName (String name, Pageable pageable) {
        return alcoholRepository.findByNameContains(name,pageable).map(alcohol -> new AlcoholResponseDto(alcohol));
    }

    //술 상세페이지 리턴
    public AlcoholDetailResponseDto getAlcoholDetail(Long id) {
        Optional<Alcohol> alcoholDetail = alcoholRepository.findById(id);
            return new AlcoholDetailResponseDto(alcoholDetail.orElseThrow(()-> new IllegalArgumentException("no such alcohol")));
    }

    //로그 찍는 메서드
    public void printLog (Long u_id, Long a_id) {
        logger.info("u_id: "+u_id+" a_id: "+a_id);
    }


    public  Page<AlcoholResponseDto> findByPriceAndDegree (Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable) {

        Page<Alcohol> resultEntity ; //결과를 담기 위한 객체

        if (degreeTo == 30 && priceTo == 100000) {
            //30도 이상, 10만원이상 선택시
            resultEntity= alcoholRepository.findByDegreeGreaterThanAndPriceGreaterThan(degreeFrom, priceFrom, pageable);
        } else if (degreeTo == 30) {
            //30도 이상만 선택시
            resultEntity = alcoholRepository.findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(priceFrom, priceTo, degreeFrom, pageable);
        } else if (priceTo == 100000) {
            //10만원 이상만 선택시
            resultEntity = alcoholRepository.findByDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(degreeFrom, degreeTo, priceFrom, pageable);
        } else {
            //그외
            resultEntity = alcoholRepository.findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    priceFrom, priceTo, degreeFrom, degreeTo, pageable);
        }
        return resultEntity.map(alcohol -> new AlcoholResponseDto(alcohol));
    }

    public Page<AlcoholResponseDto> findByTypeAndPriceAndDegree (
            String type, Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable) {

        Type enumType = Type.valueOf(type); //string -> enum 으로 고치는 작업
        Page<Alcohol> resultEntity ; //결과를 잠시 담기 위한 객체

        if (degreeTo==30 && priceTo== 100000) {
            //30도 이상, 10만원이상 선택시
            resultEntity= alcoholRepository.findByTypeAndDegreeGreaterThanAndPriceGreaterThan(enumType,degreeFrom, priceFrom, pageable);
        }
        else if (degreeTo==30) {
            //30도 이상만 선택시
            resultEntity= alcoholRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(enumType, priceFrom, priceTo, degreeFrom, pageable);
        }
        else if (priceTo==100000) {
            //10만원 이상만 선택시
            resultEntity= alcoholRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(enumType, degreeFrom, degreeTo, priceFrom,pageable);
        }
        else {
            //그외
            resultEntity= alcoholRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    enumType, priceFrom, priceTo, degreeFrom, degreeTo, pageable);
        }
        return resultEntity.map(alcohol -> new AlcoholResponseDto(alcohol));
    }

    public double getAlcoholPerSoju (AlcoholDetailResponseDto alcohol) {
        double alcoholWeight = alcohol.getDegree() * alcohol.getCapacity() * 0.8; //이 술에 들어있는 알코올 중량(g)
        System.out.println(ALCOHOL_IN_SOJU);
        System.out.println(alcoholWeight);
        return Math.round((alcoholWeight/ALCOHOL_IN_SOJU*100)/100.0); //소주 한병으로 환산한 알코올 량(반올림)
    }




}
