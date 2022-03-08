package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigDecimal;
import java.util.List;

public interface BoardRepository extends JpaRepository<Alcohol, Long> {

    Page<Alcohol> findByNameContains (String name, Pageable pageable); //이름 검색

    //필터 검색 안할떄
    Page<Alcohol> findAll (Pageable pageable);

    //타입 +도수 30도 이상 선택했을때
    Page<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(
            Type type, Integer fromP, Integer toP,
            Double fromD, Pageable pageable
    );
    //타입 +가격 10만원 이상 선택했을때
    Page<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(
            Type type,Double fromD, Double toD, Integer fromP, Pageable pageable
    );

    //타입 +가격 10만원 이상, 도수 30도 이상 선택했을때
    Page<Alcohol> findByTypeAndDegreeGreaterThanAndPriceGreaterThan(
            Type type, Double fromD,
            Integer fromP, Pageable pageable
    );
    //타입 +그 외의 경우
    Page<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan
    (Type type, Integer fromP, Integer toP,
     Double fromD, Double toD, Pageable pageable);


    //도수 30도 이상 선택했을때
    Page<Alcohol> findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(
            Integer fromP, Integer toP,
            Double fromD, Pageable pageable
    );
    //가격 10만원 이상 선택했을때
    Page<Alcohol> findByDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(
            Double fromD, Double toD, Integer fromP, Pageable pageable
    );

    //가격 10만원 이상, 도수 30도 이상 선택했을때
    Page<Alcohol> findByDegreeGreaterThanAndPriceGreaterThan(
             Double fromD,
            Integer fromP, Pageable pageable
    );
    //그 외의 경우
    Page<Alcohol> findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan
    (Integer fromP, Integer toP,
     Double fromD, Double toD, Pageable pageable);


}
