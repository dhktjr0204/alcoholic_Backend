package graduation.alcoholic.web.main;

import graduation.alcoholic.domain.entity.CollectionContent;
import graduation.alcoholic.domain.entity.CollectionInfo;
import graduation.alcoholic.domain.repository.CollectionContentRepository;
import graduation.alcoholic.domain.repository.CollectionInfoRepository;
import graduation.alcoholic.web.collection.collectionContent.dto.CollectionContentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainService {
    private final CollectionInfoRepository collectionInfoRepository;
    private final CollectionContentRepository collectionContentRepository;

    public List<MainResponseDto> getRandomCollection () {
        List<MainResponseDto> result = new ArrayList<>();

        List<CollectionInfo> randomCollection = collectionInfoRepository.findRandomCollection();

        for (CollectionInfo info : randomCollection) {
            List<CollectionContentResponseDto> content = collectionContentRepository.findByCollectionInfo(info);
            result.add(toResponseDto(info, content));
        }

        return result;

    }


    public MainResponseDto toResponseDto (CollectionInfo info, List<CollectionContentResponseDto> content){
        return new MainResponseDto(info, content);
    }


}
