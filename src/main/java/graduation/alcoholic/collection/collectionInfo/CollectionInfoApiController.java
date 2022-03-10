package graduation.alcoholic.collection.collectionInfo;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CollectionInfoApiController {

    private final CollectionInfoService collectionInfoService;

    @PostMapping("/collectioninfo")
    public Long save(CollectionInfoSaveRequestDto collectionInfoSaveRequestDto) {
        return collectionInfoService.save(collectionInfoSaveRequestDto);
    }

    @PutMapping("/collectioninfo/{id}")

    @GetMapping("/collectioninfo/{id}")

    @GetMapping("/collectioninfo/user/{user_id}")

    @DeleteMapping("/collectioninfo/{id}")
}
