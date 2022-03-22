package graduation.alcoholic.domain.repository;

import graduation.alcoholic.web.collection.collectionContent.dto.CollectionContentResponseDto;
import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionContentId;
import graduation.alcoholic.domain.entity.CollectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectionContentRepository extends JpaRepository<CollectionContent, CollectionContentId> {

    @Query("SELECT c FROM CollectionContent c WHERE c.collection = :collection")
    public List<CollectionContentResponseDto> findByCollectionInfo(@Param("collection")CollectionInfo collectionInfo);
}
