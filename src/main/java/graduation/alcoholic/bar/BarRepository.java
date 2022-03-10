package graduation.alcoholic.bar;

import graduation.alcoholic.domain.Bar;
import graduation.alcoholic.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarRepository extends JpaRepository<Bar,Long> {
    Page<Bar> findByTitleContains(String title,Pageable pageable);
    Page<Bar> findByLocationContains(String location,Pageable pageable);
    Page<Bar> findAll(Pageable pageable);
}
