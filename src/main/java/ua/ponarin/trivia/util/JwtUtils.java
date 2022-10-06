package ua.ponarin.trivia.util;

import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtUtils {
    public <T> T parseClaim(String key, Class<T> clazz, String token) {
        var tokenWithoutSignature = getTokenWithoutSignature(token);
        return Jwts.parserBuilder()
                .build()
                .parseClaimsJwt(tokenWithoutSignature)
                .getBody()
                .get(key, clazz);
    }

    private String getTokenWithoutSignature(String token) {
        var i = token.lastIndexOf('.');
        return token.substring(0, i+1);
    }
}
