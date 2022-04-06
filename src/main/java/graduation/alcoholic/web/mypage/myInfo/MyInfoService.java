package graduation.alcoholic.web.mypage.myInfo;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final UserRepository userRepository;

    public MyInfoResponseDto getUserInfoDto(Long u_id){
        User entity = userRepository.findById(u_id).orElseThrow();
        return new MyInfoResponseDto(entity) ;
    }

    public MyInfoResponseDto updateCapacity (User entity, MyInfoUpdateDto updateDto) {
        entity.setCapacity(updateDto.getCapacity());
        userRepository.save(entity);
        return new MyInfoResponseDto(entity) ;
    }

    public MyInfoResponseDto updateNickname (User entity, MyInfoUpdateDto updateDto) {
        entity.setNickname(updateDto.getNickname());
        userRepository.save(entity);
        return new MyInfoResponseDto(entity) ;
    }


    public User getUserInfoEntity ( Long u_id) {
        return userRepository.findById(u_id).orElseThrow();
    }

}
