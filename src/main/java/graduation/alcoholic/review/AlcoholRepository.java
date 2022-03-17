package graduation.alcoholic.review;

import graduation.alcoholic.domain.Alcohol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlcoholRepository extends JpaRepository<Alcohol, Long> {
}
