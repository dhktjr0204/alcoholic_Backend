package graduation.alcoholic.alcohol;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AlcoholService {
    private final AlcoholRepository alcoholRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public Page<AlcoholResponseDto> searchByName (String name, Pageable pageable) {
        return alcoholRepository.findByNameContains(name,pageable).map(alcohol -> new AlcoholResponseDto(alcohol));
    }

    public AlcoholDetailResponseDto getAlcoholDetail(Long id) {
        Alcohol alcoholDetail = alcoholRepository.findById(id).orElseThrow();
        return new AlcoholDetailResponseDto(alcoholDetail);
    }

    public void printLog (Long u_id, Long a_id) {
        logger.info("u_id: "+u_id+" a_id: "+a_id);
    }


    public  Page<AlcoholResponseDto> findByPriceAndDegree (
            Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable
    ) {
        Page<Alcohol> resultEntity ;
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
                    priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
        return resultEntity.map(alcohol -> new AlcoholResponseDto(alcohol));
    }

    public Page<AlcoholResponseDto> findByTypeAndPriceAndDegree (
            String type, Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable
            ) {

        Type enumType = Type.valueOf(type);
        Page<Alcohol> resultEntity ;
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
                    enumType, priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
        return resultEntity.map(alcohol -> new AlcoholResponseDto(alcohol));
    }




}
