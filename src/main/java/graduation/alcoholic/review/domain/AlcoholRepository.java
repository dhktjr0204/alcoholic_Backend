package graduation.alcoholic.review.domain;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {


    List<Alcohol> findByType(Type type);
}
