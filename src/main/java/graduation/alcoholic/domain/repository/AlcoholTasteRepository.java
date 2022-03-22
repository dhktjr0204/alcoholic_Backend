package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.AlcoholTaste;
import graduation.alcoholic.domain.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlcoholTasteRepository extends JpaRepository<AlcoholTaste, Long> {

    //@Query("SELECT a FROM AlcoholTaste a WHERE a.type = :type")
    List<AlcoholTaste> findByType(@Param("type") Type type);

    @Query("SELECT a FROM AlcoholTaste a WHERE a.type = '증류주' AND a.degree <= 25")
    List<AlcoholTaste> findSojuDegreeLessThanequal25();

    @Query("SELECT a FROM AlcoholTaste a WHERE a.type = '증류주' AND a.degree > 25")
    List<AlcoholTaste> findSojuDegreeGreaterThan25();


}
