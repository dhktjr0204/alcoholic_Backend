package graduation.alcoholic.board;

import graduation.alcoholic.Zzim.ZzimRepository;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.Zzim;
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
    private  final ZzimRepository zzimRepository;

    public Optional<Alcohol> getAlcoholDetail(Long id) {
        return boardRepository.findById(id);
    }


    public  List<Alcohol> findByPriceAndDegree (
            Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo
    ) {
        List<Alcohol> res = new ArrayList<>();
        res.addAll(findByTypeAndPriceAndDegree(Type.청주.toString(), priceFrom, priceTo, degreeFrom, degreeTo));
        res.addAll(findByTypeAndPriceAndDegree(Type.증류주.toString(), priceFrom, priceTo, degreeFrom, degreeTo));
        res.addAll(findByTypeAndPriceAndDegree(Type.탁주.toString(), priceFrom, priceTo, degreeFrom, degreeTo));
        res.addAll(findByTypeAndPriceAndDegree(Type.과실주.toString(), priceFrom, priceTo, degreeFrom, degreeTo));

        return res;
    }

    public List<Alcohol> findByTypeAndPriceAndDegree (
            String type, Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo
            ) {

        Type enumType = Type.valueOf(type);

        if (degreeTo==30 && priceTo== 100000) {
            //30도 이상, 10만원이상 선택시
            return boardRepository.findByTypeAndDegreeGreaterThanAndPriceGreaterThan(enumType,degreeFrom, priceFrom);
        }
        else if (degreeTo==30) {
            //30도 이상만 선택시
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(enumType, priceFrom, priceTo, degreeFrom);
        }
        else if (priceTo==100000) {
            //10만원 이상만 선택시

            return boardRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(enumType, degreeFrom, degreeTo, priceFrom );
        }
        else {
            //그외
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    enumType, priceFrom, priceTo,
                    degreeFrom, degreeTo
            );
        }
    }

    public void addZzim (User user, Long a_id) {
      //  zzimRepository.save(Zzim.builder(user, boardRepository.getById(a_id)));
    }




}
