package graduation.alcoholic.login.domain.auth.config;
import graduation.alcoholic.login.domain.auth.jwt.AuthTokenProvider;
import graduation.alcoholic.login.domain.auth.jwt.JwtAuthenticationFilter;
import graduation.alcoholic.login.domain.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthTokenProvider authTokenProvider;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**","/webjars/**","/bar/**","/auth/login");
        //swagger로 요청이 들어 올 땐 헤더에 access token이 없으므로 jwt filter를 타지 않도록 처리함
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authTokenProvider);
        http.authorizeRequests() //시큐리티 처리에 httpServletRequest사용
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/auth/**").permitAll()//어떤 링크 어떤 메소드이든지 다 접근 가능
                .anyRequest().authenticated().and() // 해당 요청을 인증된 사용자만 사용 가능
                .headers()
                .frameOptions()
                .sameOrigin().and()
                .cors().and()//프론트랑 포트번호가 다를때 오류 무시
                .csrf().disable()//해킹방지
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//jwt토큰은 기본적으로 session을 사용하지 않기때문에 STATELESS유지
                .and()
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
//        http.httpBasic().disable();

    }
}