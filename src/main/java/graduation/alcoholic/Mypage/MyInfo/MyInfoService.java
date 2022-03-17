package graduation.alcoholic.Mypage.MyInfo;

import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final UserRepository userRepository;

    public MyInfoResponseDto getUserInfo(String email){
        User entity = userRepository.findByEmail(email);
        return new MyInfoResponseDto(entity.getName(),entity.getEmail(),entity.getAge_range()
                ,entity.getCapacity(), entity.getSex()) ;
    }

    public MyInfoResponseDto updateCapacity (String email, BigDecimal capacity) {
        User entity = userRepository.findByEmail(email);
        entity.update(capacity);
        userRepository.save(entity);
        return new MyInfoResponseDto(entity.getName(),entity.getEmail(),entity.getAge_range()
                ,entity.getCapacity(), entity.getSex()) ;
    }

}
