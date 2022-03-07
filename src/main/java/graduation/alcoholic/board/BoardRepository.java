package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigDecimal;
import java.util.List;

public interface BoardRepository extends JpaRepository<Alcohol, Long> {

    List<Alcohol> findByNameContains (String name); //이름 검색

    //필터 검색 안할떄
    Page<Alcohol> findAll (Pageable pageable);

    //도수 30도 이상 선택했을때
    List<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(
            Type type, Integer fromP, Integer toP,
            Double fromD
    );

    //가격 10만원 이상 선택했을때
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(
            Type type,Double fromD, Double toD, Integer fromP
    );

    //가격 10만원 이상, 도수 30도 이상 선택했을때
    List<Alcohol> findByTypeAndDegreeGreaterThanAndPriceGreaterThan(
            Type type, Double fromD,
            Integer fromP
    );

    //그 외의 경우
    List<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan
    (Type type, Integer fromP, Integer toP,
     Double fromD, Double toD);


}
