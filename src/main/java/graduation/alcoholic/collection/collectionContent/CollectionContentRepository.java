package graduation.alcoholic.collection.collectionContent;

import graduation.alcoholic.domain.CollectionContent;
import graduation.alcoholic.domain.CollectionContentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionContentRepository extends JpaRepository<CollectionContent, CollectionContentId> {
}
