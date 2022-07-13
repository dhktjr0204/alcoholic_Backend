package graduation.alcoholic.domain.repository;

import graduation.alcoholic.domain.entity.Alcohol;
import graduation.alcoholic.web.collection.collectionContent.dto.CollectionContentResponseDto;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionContentId;
import graduation.alcoholic.domain.entity.CollectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CollectionContentRepository extends JpaRepository<CollectionContent, CollectionContentId> {

    @Query("SELECT c FROM CollectionContent c WHERE c.collection = :collection")
    public List<CollectionContentResponseDto> findByCollectionInfo(@Param("collection")CollectionInfo collectionInfo);

    @Query("SELECT c FROM CollectionContent c WHERE c.collection = :collection AND c.alcohol = :alcohol")
    public Optional<CollectionContent> findByCollectionInfoAndAlcohol(@Param("collection")CollectionInfo collectionInfo, @Param("alcohol") Alcohol alcohol);
}
