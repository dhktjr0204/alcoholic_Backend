package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarRepository extends JpaRepository<Bar,Long> {
    List<Bar> findByuser(User user);
}
