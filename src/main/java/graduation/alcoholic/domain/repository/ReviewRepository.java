package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.domain.entity.Review;
import graduation.alcoholic.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByAlcoholOrderByModifiedDateDesc(Alcohol alcohol);

    List<Review> findByUserOrderByModifiedDateDesc(User user);

    Optional<Review> findByUserAndAlcohol(User user,Alcohol alcohol);


}
