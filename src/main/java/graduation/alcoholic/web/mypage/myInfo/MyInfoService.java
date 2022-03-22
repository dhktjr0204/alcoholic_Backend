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

    public MyInfoResponseDto getUserInfoDto(String email){
        User entity = userRepository.findByEmail(email);
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


    public User getUserInfoEntity (String email) {
        return userRepository.findByEmail(email);
    }

}
