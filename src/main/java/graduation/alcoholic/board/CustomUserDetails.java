package graduation.alcoholic.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Getter
@Setter
@Data
public class CustomUserDetails implements UserDetails {

    private String id;	// DB에서 PK 값
    private String name;
    private String email;	//이메일
    private String password;	// 비밀번호
    private String sex;
    private String age_range;	//나이대

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    public String getSex() {
        return sex;
    }
    public String getEmail() {
        return email;
    }

    public String getAgeRange() {
        return age_range;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
