package mx.com.server.cae.security.auth.apiKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import mx.com.server.cae.services.MyUserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/*
 *referencia: https://spring.io/guides/tutorials/spring-security-and-angular-js/
 */
@Component
public class ApiKeyAuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${security.jwt.token.secretKey}")
    private String jwtSecret;

    @Value("${security.jwt.token.expiration}")
    private int jwtExpirationMs;

    // generate token for user
    public String generateToken(MyUserPrincipal loadedUser) {
        return doGenerateToken(loadedUser.getId(), loadedUser.getEmail(), loadedUser.getAuthorities());
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(String userId, String subject, Collection<? extends GrantedAuthority> authorities) {
        Date currentDateAt = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .claim("id", userId)
                .claim("authorities", authorities)
                .setIssuedAt(currentDateAt)
                .signWith(SignatureAlgorithm.HS256, this.jwtSecret)
                .compact();
    }

    public boolean isValidedApiKeyToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
            return true;
            //OK, we can trust this JWT
        } catch (JwtException e) {
            log.error("Invalid JWT token" + e.getMessage());
            //don't trust the JWT!
        }
        return false;
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            claims = null;
        }
        return claims;
    }

}
