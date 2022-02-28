package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Optional<Alcohol> getAlcoholDetail(Long id) {
        return boardRepository.findById(id);
    }

    public List<Alcohol> findByTypeAndPriceAndDegree (
            String type, Long priceFrom, Long priceTo,
            Double degreeFrom, Double degreeTo,
            Pageable pageable) {

        if (degreeTo==30 && priceTo== 100000) {
            //30도 이상, 10만원이상 선택시
            return boardRepository.findByTypeAndDegreeGreaterThanAndPriceGreaterThan(type, priceFrom, degreeFrom, pageable);
        }
        else if (degreeTo==30) {
            //30도 이상만 선택시
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(type, priceFrom, priceTo, degreeFrom,pageable);
        }
        else if (priceTo==100000) {
            //10만원 이상만 선택시
            return boardRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(type, priceFrom, degreeFrom, degreeTo, pageable);
        }
        else {
            //그외
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    type, priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
    }






}
