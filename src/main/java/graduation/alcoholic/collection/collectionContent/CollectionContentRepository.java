package graduation.alcoholic.collection.collectionContent;

import graduation.alcoholic.domain.CollectionContent;
import graduation.alcoholic.domain.CollectionContentId;
import graduation.alcoholic.domain.CollectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectionContentRepository extends JpaRepository<CollectionContent, CollectionContentId> {

    @Query("SELECT c FROM CollectionContent c WHERE c.collection = :collection")
    public List<CollectionContentResponseDto> findByCollectionInfo(@Param("collection")CollectionInfo collectionInfo);
}
