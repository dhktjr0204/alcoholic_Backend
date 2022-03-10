package graduation.alcoholic.collection.collectionInfo;

import graduation.alcoholic.domain.CollectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionInfoRepository extends JpaRepository<CollectionInfo, Long> {
}
