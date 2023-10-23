package wallet_service.out.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtProvider {

    private static final long TOKEN_EXPIRATION = 1;
    private static final String SECRET_KEY = "YourSecretKey";

    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(Date.from(ZonedDateTime.now().plus(TOKEN_EXPIRATION, ChronoUnit.HOURS).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

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