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
        return new MyInfoResponseDto(entity.getName(),entity.getEmail(),entity.getAge_range()
                ,entity.getCapacity(), entity.getSex(), entity.getNickname()) ;
    }

    public MyInfoResponseDto updateCapacityAndNickname (User entity, BigDecimal capacity, String nickname) {
        entity.setCapacity(capacity);
        entity.setNickname(nickname);
        userRepository.save(entity);
        return new MyInfoResponseDto(entity.getName(),entity.getEmail(),entity.getAge_range()
                ,entity.getCapacity(), entity.getSex(), entity.getNickname()) ;
    }


    public User getUserInfoEntity ( Long u_id) {
        return userRepository.findById(u_id).orElseThrow();
    }

}
