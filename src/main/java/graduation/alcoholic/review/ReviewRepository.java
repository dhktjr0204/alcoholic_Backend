package graduation.alcoholic.review;

import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.alcohol = :alcohol")
    List<Review> findByAlcohol(@Param("alcohol") Alcohol alcohol);

    @Query("SELECT r FROM Review  r WHERE r.user = :user")
    List<Review> findByUser(@Param("user") User user);
}
