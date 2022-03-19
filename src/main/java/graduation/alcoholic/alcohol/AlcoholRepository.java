package graduation.alcoholic.alcohol;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {

//    @Query(value = "SELECT a " +
//            " FROM Alcohol a" +
//                " LEFT JOIN Review r on a.id = r.id" +
//            " WHERE name LIKE %?1%" +
//            " ORDER BY COUNT(r.id)")
    Page<Alcohol> findByNameContains (String name, Pageable pageable); //이름 검색

//    //필터 검색 안할떄
//    Page<Alcohol> findAll (Pageable pageable);

    //타입 +도수 30도 이상 선택했을때
//    @Query(value = "SELECT a FROM Alcohol a LEFT JOIN Review r on a.id = r.id" +
//            " WHERE a.type = :type" +
//            " AND a.price BETWEEN :priceFrom AND :priceTo AND a.degree >= :degreeFrom" +
//            " ORDER BY COUNT(r.id)")
    Page<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(
            @Param("type") Type type,
            @Param("priceFrom") Integer fromP,
            @Param("priceTo") Integer toP,
            @Param("degreeFrom") Double fromD, Pageable pageable
    );

    //타입 +가격 10만원 이상 선택했을때
    Page<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD,
            @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, Pageable pageable
    );

    //타입 +가격 10만원 이상, 도수 30도 이상 선택했을때
    Page<Alcohol> findByTypeAndDegreeGreaterThanAndPriceGreaterThan(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD,
            @Param("priceFrom") Integer fromP,
            Pageable pageable
    );

    //타입 +그 외의 경우
    Page<Alcohol> findByTypeAndPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan
    (@Param("type") Type type, @Param("priceFrom") Integer fromP,
     @Param("priceTo") Integer toP,
     @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD, Pageable pageable);


    //도수 30도 이상 선택했을때
    Page<Alcohol> findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThan(
            @Param("priceFrom") Integer fromP,
            @Param("priceTo") Integer toP,
            @Param("degreeFrom") Double fromD, Pageable pageable
    );
    //가격 10만원 이상 선택했을때
    Page<Alcohol> findByDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThan(
            @Param("degreeFrom") Double fromD,
            @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, Pageable pageable
    );

    //가격 10만원 이상, 도수 30도 이상 선택했을때
    Page<Alcohol> findByDegreeGreaterThanAndPriceGreaterThan(
            @Param("degreeFrom") Double fromD,
            @Param("priceFrom") Integer fromP, Pageable pageable
    );
    //그 외의 경우
    Page<Alcohol> findByPriceGreaterThanAndPriceLessThanAndDegreeGreaterThanAndDegreeLessThan
    (@Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
     @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD, Pageable pageable);


}
