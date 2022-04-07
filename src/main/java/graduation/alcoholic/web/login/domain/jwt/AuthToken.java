package graduation.alcoholic.web.login.domain.jwt;


import graduation.alcoholic.domain.enums.RoleType;
import graduation.alcoholic.web.login.domain.exception.TokenValidFailedException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    Exception ExpiredJwtException;

    private static final String AUTHORITIES_KEY = "role";
    AuthToken(Long id, RoleType roleType, Date expiry, Key key) {
        String role=roleType.toString();
        this.key = key;
        this.token = createAuthToken(id,role, expiry);
    }

    private String createAuthToken(Long id, String role,Date expiry) {
        //토큰 builder
        return Jwts.builder()
                .setSubject(id.toString())//토큰 용도
                .claim(AUTHORITIES_KEY,role)
                .signWith(key, SignatureAlgorithm.HS256)//HS256과 key로 sign
                .setExpiration(expiry)//토큰 만료 시간 설정
                .compact();//토큰 생성
    }

//토큰 검증
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)//set key
                    .build()
                    .parseClaimsJws(token)//파싱 및 검증, 실패 시 에러
                    .getBody();;
            return true;
        } catch (MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new TokenValidFailedException();
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("만료된 JWT 토큰입니다.");
            throw expiredJwtException;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new TokenValidFailedException();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new TokenValidFailedException();
        }
    }
    public Claims getTokenClaims(){
        return Jwts.parserBuilder()
                .setSigningKey(key)//set key
                .build()
                .parseClaimsJws(token)//파싱 및 검증, 실패 시 에러
                .getBody();
    }
}