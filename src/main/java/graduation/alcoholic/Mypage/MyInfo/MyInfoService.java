package graduation.alcoholic.Mypage.MyInfo;

import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.auth.enumerate.RoleType;
import graduation.alcoholic.login.domain.member.UserDto;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final UserRepository userRepository;

    public UserDto getUserInfo(String email){
        User entity = userRepository.findByEmail(email);
        return new UserDto(entity.getName(),entity.getEmail(),entity.getAge_range()
                ,"임시 주량", entity.getSex(), RoleType.ROLE_USER) ;
    }

    public UserDto updateCapacity (String email, BigDecimal capacity) {
        User entity = userRepository.findByEmail(email);
        return new UserDto(entity.getName(),entity.getEmail(),entity.getAge_range()
                ,"바꾼주량", entity.getSex(), RoleType.ROLE_USER) ;
    }

}
