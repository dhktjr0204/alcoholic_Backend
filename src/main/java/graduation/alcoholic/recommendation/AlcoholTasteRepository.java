package graduation.alcoholic.recommendation;

import graduation.alcoholic.domain.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlcoholTasteRepository extends JpaRepository<AlcoholTaste, Long> {

    @Query("SELECT a FROM AlcoholTaste a WHERE a.type = :type")
    List<AlcoholTaste> findByType(@Param("type") Type type);
}
