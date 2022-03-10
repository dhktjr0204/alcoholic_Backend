package graduation.alcoholic.board;

import graduation.alcoholic.Zzim.ZzimRepository;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.Zzim;
import graduation.alcoholic.domain.ZzimId;
import graduation.alcoholic.domain.enums.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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


    public  Page<Alcohol> findByPriceAndDegree (
            Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable
    ) {
        if (degreeTo == 30 && priceTo == 100000) {
            //30도 이상, 10만원이상 선택시
            return boardRepository.findByDegreeGreaterThanAndPriceGreaterThan(degreeFrom, priceFrom, pageable);
        } else if (degreeTo == 30) {
            //30도 이상만 선택시
            return boardRepository.findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(priceFrom, priceTo, degreeFrom, pageable);
        } else if (priceTo == 100000) {
            //10만원 이상만 선택시
            return boardRepository.findByDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(degreeFrom, degreeTo, priceFrom, pageable);
        } else {
            //그외
            return boardRepository.findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
    }

    public Page<Alcohol> findByTypeAndPriceAndDegree (
            String type, Integer priceFrom, Integer priceTo,
            Double degreeFrom, Double degreeTo, Pageable pageable
            ) {

        Type enumType = Type.valueOf(type);

        if (degreeTo==30 && priceTo== 100000) {
            //30도 이상, 10만원이상 선택시
            return boardRepository.findByTypeAndDegreeGreaterThanAndPriceGreaterThan(enumType,degreeFrom, priceFrom, pageable);
        }
        else if (degreeTo==30) {
            //30도 이상만 선택시
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(enumType, priceFrom, priceTo, degreeFrom, pageable);
        }
        else if (priceTo==100000) {
            //10만원 이상만 선택시

            return boardRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(enumType, degreeFrom, degreeTo, priceFrom,pageable);
        }
        else {
            //그외
            return boardRepository.findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan(
                    enumType, priceFrom, priceTo,
                    degreeFrom, degreeTo, pageable
            );
        }
    }

    public void addZzim (User user, Long a_id) {
        Alcohol alcohol = boardRepository.findById(a_id).get();
        ZzimId zzimId = new ZzimId();
        zzimId.setAlcohol_id(alcohol.getId());
        zzimId.setUser_id(user.getId());
        //Zzim zzim = new Zzim(user, alcohol, zzimId);
       // zzimRepository.save(zzim);
    }




}
