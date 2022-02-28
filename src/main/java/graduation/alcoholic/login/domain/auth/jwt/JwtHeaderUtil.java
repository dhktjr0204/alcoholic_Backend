package graduation.alcoholic.login.domain.auth.jwt;

import javax.servlet.http.HttpServletRequest;

public class JwtHeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            System.out.println("베리어 뗀거"+headerValue.substring(TOKEN_PREFIX.length()));
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}