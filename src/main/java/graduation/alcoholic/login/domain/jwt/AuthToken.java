package graduation.alcoholic.login.domain.jwt;


import graduation.alcoholic.login.domain.enumerate.RoleType;
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

    private static final String AUTHORITIES_KEY = "role";
    AuthToken(String email, RoleType roleType, Date expiry, Key key) {
        String role=roleType.toString();
        this.key = key;
        this.token = createAuthToken(email,role, expiry);
    }

    private String createAuthToken(String email, String role,Date expiry) {
        //토큰 builder
        return Jwts.builder()
                .setSubject(email)//토큰 용도
                .claim(AUTHORITIES_KEY,role)
                .signWith(key, SignatureAlgorithm.HS256)//HS256과 key로 sign
                .setExpiration(expiry)//토큰 만료 시간 설정
                .compact();//토큰 생성
    }
//토큰 검증
    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)//set key
                    .build()
                    .parseClaimsJws(token)//파싱 및 검증, 실패 시 에러
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {//토큰이 만료되었을 경우
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {//그 외 에러
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    public String findTokentoEmail(){
        return Jwts.parserBuilder()
                .setSigningKey(key)//set key
                .build()
                .parseClaimsJws(token)//파싱 및 검증, 실패 시 에러
                .getBody().getSubject();
    }
}