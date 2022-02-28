package graduation.alcoholic.login.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String name;
    private String email;
    private String age_range;
    private String sex;

    private String JwtToken;//jwt토큰
    private Boolean isNewMember;
}