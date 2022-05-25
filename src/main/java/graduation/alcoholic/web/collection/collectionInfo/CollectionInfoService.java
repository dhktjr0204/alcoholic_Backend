package graduation.alcoholic.web.collection.collectionInfo;

import graduation.alcoholic.domain.entity.CollectionInfo;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.domain.repository.CollectionInfoRepository;
import graduation.alcoholic.domain.repository.UserRepository;

import graduation.alcoholic.web.collection.collectionInfo.dto.CollectionInfoResponseDto;
import graduation.alcoholic.web.collection.collectionInfo.dto.CollectionInfoSaveRequestDto;
import graduation.alcoholic.web.collection.collectionInfo.dto.CollectionInfoUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CollectionInfoService {


    private final CollectionInfoRepository collectionInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(Long id, CollectionInfoSaveRequestDto requestDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        requestDto.setUser(user);
        checkCollectionDuplication(requestDto);
        System.out.println(requestDto.getTitle());
        return collectionInfoRepository.save(requestDto.toEntity()).getId();
    }


    @Transactional(readOnly = true)
    public void checkCollectionDuplication(CollectionInfoSaveRequestDto requestDto) {

        Optional<CollectionInfo> collectionInfo = collectionInfoRepository.findByTitle(requestDto.getTitle());

        if (collectionInfo.isPresent()) {
            throw new IllegalStateException("중복된 컬렉션입니다.");
        }
    }


    @Transactional
    public Long update(Long id, CollectionInfoUpdateRequestDto collectionInfoUpdateRequestDto) {
        CollectionInfo collectionInfo = collectionInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션이 없습니다 id: " + id));

        collectionInfo.update(collectionInfoUpdateRequestDto.getTitle(), collectionInfoUpdateRequestDto.getDescription());

        return id;
    }


    @Transactional(readOnly = true)
    public List<CollectionInfoResponseDto> findByUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        return collectionInfoRepository.findByUser(user);
    }

    @Transactional
    public void delete(Long id) {
        CollectionInfo collectionInfo = collectionInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션이 없습니다 id: " + id));

        collectionInfoRepository.delete(collectionInfo);
    }

}
