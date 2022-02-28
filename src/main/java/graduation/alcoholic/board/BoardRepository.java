package graduation.alcoholic.board;

import graduation.alcoholic.domain.Alcohol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BoardRepository extends JpaRepository<Alcohol, Long> {

    List<Alcohol> findByNameContains (String name); //이름 검색

    //필터 검색 안할떄
    Page<Alcohol> findAll (Pageable pageable);

    //도수 30도 이상 선택했을때
    List<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(
            String type, Long fromP, Long toP,
            Double fromD, Pageable pageable
    );

    //가격 10만원 이상 선택했을때
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(
            String type, Long fromP,
            Double fromD, Double toD, Pageable pageable
    );

    //가격 10만원 이상, 도수 30도 이상 선택했을때
    List<Alcohol> findByTypeAndDegreeGreaterThanAndPriceGreaterThan(
            String type, Long fromP,
            Double fromD, Pageable pageable
    );

    //그 외의 경우
    List<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan
    (String type, Long fromP, Long toP,
     Double fromD, Double toD,
     Pageable pageable);


}
