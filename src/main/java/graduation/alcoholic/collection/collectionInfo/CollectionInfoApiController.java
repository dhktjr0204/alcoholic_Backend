package graduation.alcoholic.collection.collectionInfo;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CollectionInfoApiController {

    private final CollectionInfoService collectionInfoService;

    @PostMapping("/collectioninfo")
    public Long save(CollectionInfoSaveRequestDto collectionInfoSaveRequestDto) {
        return collectionInfoService.save(collectionInfoSaveRequestDto);
    }

    @PutMapping("/collectioninfo/{id}")
    public Long update(@PathVariable Long id, CollectionInfoUpdateRequestDto collectionInfoUpdateRequestDto) {
        return collectionInfoService.update(id, collectionInfoUpdateRequestDto);
    }

//    @GetMapping("/collectioninfo/{id}")


    @GetMapping("/collectioninfo/user/{user_id}")
    public List<CollectionInfoResponseDto> findByUser(@PathVariable Long user_id) {
        return collectionInfoService.findByUser(user_id);
    }

    @DeleteMapping("/collectioninfo/{id}")
    public void delete(@PathVariable Long id) {
        collectionInfoService.delete(id);
    }
}
