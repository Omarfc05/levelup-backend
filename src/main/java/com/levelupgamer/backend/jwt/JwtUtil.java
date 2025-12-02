package com.levelupgamer.backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // Clave en Base64 (256 bits). Cámbiala por una tuya en producción.
    private static final String SECRET_KEY =
            "543F6541745367566B5970337336763979244226452948404D6251655468576D";

    // 24 horas
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ============ GENERAR ============

    public String generateToken(String email, String role) {
        return createToken(Map.of("role", role), email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)              // ej: role
                .setSubject(subject)            // email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ============ VALIDAR / LEER ============

    /**
     * Valida la firma y expiración del token.
     * Si algo está mal, lanza excepción (que capturas en el filtro).
     */
    public Claims validate(String token) {
        Claims claims = extractAllClaims(token);
        Date exp = claims.getExpiration();
        if (exp.before(new Date())) {
            throw new RuntimeException("Token expirado");
        }
        return claims;
    }

    public boolean isTokenValid(String token, String email) {
        final String userEmail = extractEmail(token);
        return (userEmail.equals(email) && !isTokenExpired(token));
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
