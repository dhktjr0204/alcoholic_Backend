package graduation.alcoholic.domain.repository;

import graduation.alcoholic.web.collection.collectionInfo.dto.CollectionInfoResponseDto;
import graduation.alcoholic.domain.entity.CollectionInfo;
import graduation.alcoholic.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CollectionInfoRepository extends JpaRepository<CollectionInfo, Long> {

    @Query("SELECT c FROM CollectionInfo c WHERE c.user = :user")
    public List<CollectionInfoResponseDto> findByUser(@Param("user") User user);

    Optional<CollectionInfo> findByTitle(String title);

    @Query("SELECT COUNT(c) FROM CollectionInfo c ")
    int findCollectionSize ();

    //메인페이지 랜덤 추출(사용자꺼)
    @Query(value = "SELECT * FROM collection_info  WHERE user_id != 1 ORDER BY RAND()", nativeQuery = true)
    List<CollectionInfo> findUserRandomCollection ();

    //메인페이지 랜덤 추출(관리자꺼)
    @Query(value = "SELECT * FROM collection_info  WHERE user_id=1 ORDER BY RAND()", nativeQuery = true)
    List<CollectionInfo> findRandomCollection ();
}
