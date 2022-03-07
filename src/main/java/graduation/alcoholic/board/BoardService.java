package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Optional<Alcohol> getAlcoholDetail(Long id) {
        return boardRepository.findById(id);
    }


    public  List<Alcohol> findByPriceAndDegree (
            Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo,
            Pageable pageable
    ) {
        List<Alcohol> res = new ArrayList<>();
        res.addAll(boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
              Type.과실주, priceFrom, priceTo, degreeFrom, degreeTo, pageable) );
        res.addAll(boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                Type.증류주, priceFrom, priceTo, degreeFrom, degreeTo, pageable) );
        res.addAll(boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                Type.청주, priceFrom, priceTo, degreeFrom, degreeTo, pageable) );
        res.addAll(boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                Type.탁주, priceFrom, priceTo, degreeFrom, degreeTo, pageable) );

        return res;
    }

    public List<Alcohol> findByTypeAndPriceAndDegree (
            String type, Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo,
            Pageable pageable) {

        Type enumType = Type.valueOf(type);

        if (degreeTo==30 && priceTo== 100000) {
            //30도 이상, 10만원이상 선택시
            return boardRepository.findByTypeAndDegreeGreaterThanAndPriceGreaterThan(enumType,degreeFrom, priceFrom,  pageable);
        }
        else if (degreeTo==30) {
            //30도 이상만 선택시
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(enumType, priceFrom, priceTo, degreeFrom,pageable);
        }
        else if (priceTo==100000) {
            //10만원 이상만 선택시

            return boardRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(enumType, degreeFrom, degreeTo, priceFrom ,pageable);
        }
        else {
            //그외
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    enumType, priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
    }






}
