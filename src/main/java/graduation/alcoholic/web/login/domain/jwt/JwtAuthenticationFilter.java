package graduation.alcoholic.web.login.domain.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import graduation.alcoholic.web.login.domain.exception.TokenValidFailedException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
//얘가 프론트에서 받은 jwt토큰을 검증하는 역할을 함
    private final AuthTokenProvider tokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {
        System.out.println(request.getMethod());
        System.out.println(request.getRequestURI());
        System.out.println(request.getParameter("code"));
        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("필터 확인용"+request.getHeader("Authorization"));

        //모든 요청 헤더에 token이 있는것은 아니기 때문에
        //authoriztion bearer이 있는지 확인하고 없을때는 filter를 타지않도록 설정
        //여기서 말하는 토큰은 jwt토큰
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String tokenStr = JwtHeaderUtil.getAccessToken(request);
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);
            try {
                if (token.validate(tokenStr)) {
                    Authentication authentication = tokenProvider.getAuthentication(tokenStr,token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (ExpiredJwtException expiredJwtException){
                log.info("Expired JWT token!!");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                Map<String ,String> errorDetails=new HashMap<>();
                errorDetails.put("jwtToken","expiredJwtException");
                objectMapper.writeValue(response.getWriter(),errorDetails);
            }
            catch (TokenValidFailedException expiredJwtException){
                log.info("TokenValidFailedException token!!");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("UTF-8");
                Map<String ,String> errorDetails=new HashMap<>();
                errorDetails.put("jwtToken","invalidAccessTokenException");
                objectMapper.writeValue(response.getWriter(),errorDetails);
            }

        }
        filterChain.doFilter(request, response);
    }
}