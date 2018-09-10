package backend.common;

import backend.entity.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Clock;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public final class JWTUtils {

    private final static Clock clock = Date::new;

    public static String generateToken(Long id, String email, String name, String surname, Role role) {
        final Date createDate = clock.getToday();
        return JWT.create()
                .withAudience(
                        id == null ? null : id.toString(),
                        email,
                        name,
                        surname,
                        role == null ? null : role.toString()
                )
                .withExpiresAt(new Date(createDate.getTime() + Constants.TOKEN_TIME))
                .sign(HMAC512(Constants.SECRET.getBytes()));
    }

    public static List<String> getAudience(String token) {
        return JWT.decode(token).getAudience();
    }

    public static boolean isTokenExpired(String token) {
        try {
            return clock.getToday().after(getExpirationDate(token));
        } catch (JWTDecodeException e) {
            return true;
        }
    }

    private static Date getExpirationDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }
}
