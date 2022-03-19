package graduation.alcoholic.collection.collectionContent;

import graduation.alcoholic.domain.CollectionContentId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CollectionContentApiController {

    private final CollectionContentService collectionContentService;

    @PostMapping("/collectioncontent")
    public CollectionContentId save(CollectionContentSaveRequestDto requestDto) {
        return collectionContentService.save(requestDto);
    }

    @GetMapping("/collectioncontent/{collectioninfo_id}")
    public List<CollectionContentResponseDto> findByCollectionInfo(@PathVariable Long collectioninfo_id) {
        return collectionContentService.findByCollectionInfo(collectioninfo_id);
    }

    @DeleteMapping("/collectioncontent/{id}")
    public void delete(@PathVariable CollectionContentId id) {
        collectionContentService.delete(id);
    }
}
