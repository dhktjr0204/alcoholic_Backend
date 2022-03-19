package graduation.alcoholic.alcohol.Visit;

import graduation.alcoholic.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit,Long> {

}
