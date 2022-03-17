package graduation.alcoholic.collection.collectionInfo;

import graduation.alcoholic.domain.CollectionInfo;
import graduation.alcoholic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollectionInfoRepository extends JpaRepository<CollectionInfo, Long> {

    @Query("SELECT c FROM CollectionInfo c WHERE c.user = :user")
    public List<CollectionInfoResponseDto> findByUser(@Param("user") User user);

    Optional<CollectionInfo> findByTitle(String title);
}
