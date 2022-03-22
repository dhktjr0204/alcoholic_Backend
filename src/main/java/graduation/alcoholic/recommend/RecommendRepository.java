package graduation.alcoholic.recommend;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.enums.Taste;
import graduation.alcoholic.domain.enums.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Alcohol,Long> {

    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3AndTaste4AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2,
            @Param("taste3") Taste taste_3, @Param("taste4") Taste taste_4,
            @Param("taste5") Taste taste_5);

    //맛5 뺀거
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3AndTaste4(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2,
            @Param("taste3") Taste taste_3,@Param("taste4") Taste taste_4);

    //맛4 뺀거
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2,
            @Param("taste3") Taste taste_3, @Param("taste5") Taste taste_5);

    //맛3 뺀거
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste4AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2,
            @Param("taste4") Taste taste_4,@Param("taste5") Taste taste_5);

    //맛2 뺀거
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3AndTaste4AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste3") Taste taste_3,
            @Param("taste4") Taste taste_4,@Param("taste5") Taste taste_5);

    //맛1 뺀거
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste3AndTaste4AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste2") Taste taste_2, @Param("taste3") Taste taste_3,
            @Param("taste4") Taste taste_4,@Param("taste5") Taste taste_5);

    //맛4/맛5 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2,
            @Param("taste3") Taste taste_3);

    //맛3/맛5 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste4(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2,
            @Param("taste4") Taste taste_4);

    //맛2/맛5 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3AndTaste4(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste3") Taste taste_3,
            @Param("taste4") Taste taste_4);

    //맛1/맛5 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste3AndTaste4(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste2") Taste taste_2, @Param("taste3") Taste taste_3,
            @Param("taste4") Taste taste_4);

    //맛3/맛4 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2,
            @Param("taste5") Taste taste_5);

    //맛2/맛4 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste3") Taste taste_3,
            @Param("taste5") Taste taste_5);

    //맛1/맛4 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste3AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste2") Taste taste_2, @Param("taste3") Taste taste_3,
            @Param("taste5") Taste taste_5);

    //맛2/맛3 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste4AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste4") Taste taste_4,
            @Param("taste5") Taste taste_5);

    //맛1/맛3 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste4AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste2") Taste taste_2, @Param("taste4") Taste taste_4,
            @Param("taste5") Taste taste_5);

    //맛3/맛4/맛5 뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste2") Taste taste_2);

    //맛2/맛4/맛5 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste3") Taste taste_3);

    //맛1/맛4/맛5 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste3(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste2") Taste taste_2, @Param("taste3") Taste taste_3);

    //맛2/맛3/맛4 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1, @Param("taste5") Taste taste_5);

    //맛1/맛3/맛4 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste2") Taste taste_2, @Param("taste5") Taste taste_5);

    //맛1/맛2/맛3 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste4AndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste4") Taste taste_4, @Param("taste5") Taste taste_5);


    //맛1 제외 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste1") Taste taste_1);

    //맛2 제외 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste2") Taste taste_2);

    //맛3 제외 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste3(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste3") Taste taste_3);

    //맛4 제외 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste4(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste4") Taste taste_4);

    //맛5 제외 다뺌
    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste5(
            @Param("type") Type type,
            @Param("degreeFrom") Double fromD, @Param("degreeTo") Double toD,
            @Param("priceFrom") Integer fromP, @Param("priceTo") Integer toP,
            @Param("taste5") Taste taste_5);



//    @Query(value = "SELECT r" +
//            " FROM alcohol r WHERE r.type = :#{#entity.type}" +
//            " AND :#{#entity.start_degree} <= r.degree < :#{#entity.end_degree}" +
//            " AND :#{#entity.end_price} <= r.price < :#{#entity.end_price}" +
//            " AND r.taste_1 = :#{#entity.taste_1}" +
//            " AND r.taste_2 = :#{#entity.taste_2}" +
//            " AND r.taste_3 = :#{#entity.taste_3}" +
//            " AND r.taste_4 = :#{#entity.taste_4}" +
//            " AND r.taste_5 = :#{#entity.taste_5}" ,nativeQuery = true)
//    List<Alcohol> findByTypeAndDegreeGreaterThanAndDegreeLessThanAnd(@Param("entity") RecommendDto entity);
}
