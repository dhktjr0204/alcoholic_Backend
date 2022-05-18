package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.Bar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface BarRepository extends JpaRepository<Bar,Long> {

    Page<Bar> findByLocationContains( String location, Pageable pageable);

    @Query(value = "SELECT * FROM bar a"+ " WHERE a.location = :location"+
            " AND (a.title like %:contents% OR a.content like %:contents% OR a.location_detail like %:contents%)",nativeQuery = true)
    Page<Bar> Search(@Param("location") String location, @Param("contents")String contents, Pageable pageable);
    @Query(value = "SELECT * FROM bar a"+ " WHERE a.title like %:contents% OR a.content like %:contents% " +
            "OR a.location_detail like %:contents%",nativeQuery = true)
    Page<Bar> findByContents(String contents,Pageable pageable);


    Page<Bar> findAll(Pageable pageable);
}
