package graduation.alcoholic.web.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//카카오 로그인은 password가 없기때문에 따로 authentication provider를 작성해야함
public class kakaoAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new InternalAuthenticationServiceException("Authentication is null");
        }

        if (authentication.getCredentials() == null) {
            throw new AuthenticationCredentialsNotFoundException("Credentials is null");
        }
        String email = authentication.getName();
        UserDetails loadedUser = customUserDetailService.loadUserByUsername(email);
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        }
        /* checker */
        if (!loadedUser.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }
        if (!loadedUser.isEnabled()) {
            throw new DisabledException("User is disabled");
        }
        if (!loadedUser.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired");
        }

        /* 실질적인 인증 */
        // loadedUser password email 담겨있음 -> by CustomUserDetailService
        if (!email.equals(loadedUser.getPassword())) {
            throw new BadCredentialsException("IN KakaoAuthProvider, Email does not match stored value");
        }

        /* checker */
        if (!loadedUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("User credentials have expired");
        }
        /* 인증 완료 */
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(loadedUser, null, loadedUser.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
