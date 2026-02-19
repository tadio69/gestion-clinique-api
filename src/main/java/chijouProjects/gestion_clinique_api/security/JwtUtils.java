package chijouProjects.gestion_clinique_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    // La clé doit être assez longue pour l'algorithme HS512 (minimum 64 caractères)
    private final String SECRET_STRING = "votre_cle_secrete_tres_longue_qui_doit_faire_plus_de_64_octets_pour_la_securite_2026";

    // Génère une clé sécurisée à partir de la chaîne de caractères
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

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
