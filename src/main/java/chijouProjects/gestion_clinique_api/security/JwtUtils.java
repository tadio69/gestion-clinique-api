package chijouProjects.gestion_clinique_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${JWT_SECRET}")
    private String secretString;

    private SecretKey key;

    // Cette méthode s'exécute automatiquement une fois que Spring
    // a fini d'injecter la valeur de @Value
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(key) // Utilise l'objet SecretKey directement
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key) // Remplace setSigningKey
                .build()
                .parseSignedClaims(token) // Remplace parseClaimsJws
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // Ici tu peux logger l'erreur spécifique (ExpiredJwtException, MalformedJwtException)
            return false;
        }
    }
}
