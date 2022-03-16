package graduation.alcoholic.collection.collectionInfo;

import graduation.alcoholic.domain.CollectionInfo;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CollectionInfoService {


    private final CollectionInfoRepository collectionInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(CollectionInfoSaveRequestDto collectionInfoSaveRequestDto) {
        return collectionInfoRepository.save(collectionInfoSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, CollectionInfoUpdateRequestDto collectionInfoUpdateRequestDto) {
        CollectionInfo collectionInfo = collectionInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션이 없습니다 id: " + id));

        collectionInfo.update(collectionInfoUpdateRequestDto.getTitle(), collectionInfoUpdateRequestDto.getDescription());

        return id;
    }

//    @Transactional(readOnly = true)
//    public List<CollectionInfoResponseDto> findByUser(Long user_id) {
//
//        User user = userRepository.findBy(user_id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. user_id: " + user_id));
//
//        return collectionInfoRepository.findByUser(user);
//    }

    @Transactional
    public void delete(Long id) {
        CollectionInfo collectionInfo = collectionInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 컬렉션이 없습니다 id: " + id));

        collectionInfoRepository.delete(collectionInfo);
    }

}
