package graduation.alcoholic.web.login;

import graduation.alcoholic.domain.entity.User;
import graduation.alcoholic.domain.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::createUserDetailsByEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."+username));
    }

    private UserDetails createUserDetailsByEmail(User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRoletype().toString());
        //비밀번호로 email이 담겨있다.
        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getId()),
                user.getEmail(),
                Collections.singleton(grantedAuthority)
        );
    }
}