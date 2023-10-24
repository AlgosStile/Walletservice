package wallet_service.out.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Класс JwtProvider используется для генерации и проверки JWT токенов.
 * Данный класс испольует алгоритм HS512 для подписи токена.
 * Секретный ключ и время жизни токена задаются внутри класса.
 *
 * @author Олег Тодор
 */
public class JwtProvider {

    private static final long TOKEN_EXPIRATION = 1;
    private static final String SECRET_KEY = "YourSecretKey";

    /**
     * Метод генерирует JWT токен для указанного пользователя.
     * Используется алгоритм HS512 и секретный ключ для подписи токена.
     * Время жизни токена составляет 1 час.
     *
     * @param username логин пользователя, для которого создается токен
     * @return сгенерированный JWT токен
     */
    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(Date.from(ZonedDateTime.now().plus(TOKEN_EXPIRATION, ChronoUnit.HOURS).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * Метод проверяет валидность JWT токена, используя секретный ключ.
     * В случае успешной проверки возвращается имя пользователя (Subject), указанное при создании токена.
     * Если токен недействителен, выбрасывается RuntimeException.
     *
     * @param jwtToken токен, который необходимо проверить
     * @return логин пользователя, которому принадлежит токен
     * @throws RuntimeException если токен недействителен
     */
    public String validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwtToken);
            return claimsJws.getBody().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
