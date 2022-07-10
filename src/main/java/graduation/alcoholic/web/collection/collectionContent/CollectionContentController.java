package graduation.alcoholic.web.collection.collectionContent;

import graduation.alcoholic.domain.entity.CollectionContentId;
import graduation.alcoholic.web.collection.collectionContent.dto.CollectionContentResponseDto;
import graduation.alcoholic.web.collection.collectionContent.dto.CollectionContentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CollectionContentController {

    private final CollectionContentService collectionContentService;

    @PostMapping("/collectioncontent/{collectioninfo_id}")
    public void save(@PathVariable Long collectioninfo_id, @RequestBody CollectionContentSaveRequestDto requestDto) {
        collectionContentService.save(collectioninfo_id, requestDto);
    }

    @GetMapping("/collectioncontent/{collectioninfo_id}")
    public List<CollectionContentResponseDto> findByCollectionInfo(@PathVariable Long collectioninfo_id) {
        return collectionContentService.findByCollectionInfo(collectioninfo_id);
    }

    @DeleteMapping("/collectioncontent/{collectioninfo_id}")
    public void delete(@PathVariable Long collectioninfo_id, @RequestParam Long alcohol_id) {
        collectionContentService.delete(collectioninfo_id, alcohol_id);
    }
}
