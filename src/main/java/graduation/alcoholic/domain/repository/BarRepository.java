package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.Bar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarRepository extends JpaRepository<Bar,Long> {
    Page<Bar> findByTitleContains(String title,Pageable pageable);
    Page<Bar> findByLocationContains(String location,Pageable pageable);
    Page<Bar> findAll(Pageable pageable);
}
