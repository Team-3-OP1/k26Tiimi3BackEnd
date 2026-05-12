package backend.team3.pro.Security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key key;

    private final long expirationMs;

    public JwtUtil(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {
        try {
            // Ensure key is at least 256 bits by hashing the configured secret with
            // SHA-256.
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = md.digest(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to create JWT signing key", ex);
        }
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> extractRoles(String token) {
        var body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        Object roles = body.get("roles");
        if (roles instanceof List) {
            return ((List<?>) roles).stream().map(Object::toString).collect(Collectors.toList());
        }
        return List.of();
    }
}
