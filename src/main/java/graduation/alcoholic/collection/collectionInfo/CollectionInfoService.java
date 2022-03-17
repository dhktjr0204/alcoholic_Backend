package graduation.alcoholic.collection.collectionInfo;

import graduation.alcoholic.domain.CollectionInfo;
import graduation.alcoholic.domain.Review;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.member.UserRepository;
import graduation.alcoholic.review.ReviewSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CollectionInfoService {


    private final CollectionInfoRepository collectionInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(String email, CollectionInfoSaveRequestDto requestDto) {

        User user = userRepository.findByEmail(email);
        requestDto.setUser(user);
        checkCollectionDuplication(requestDto);
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
    public List<CollectionInfoResponseDto> findByUser(String email) {

        User user = userRepository.findByEmail(email);
        return collectionInfoRepository.findByUser(user);
    }


    @Transactional
    public void delete(Long id) {
        CollectionInfo collectionInfo = collectionInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션이 없습니다 id: " + id));

        collectionInfoRepository.delete(collectionInfo);
    }

}
