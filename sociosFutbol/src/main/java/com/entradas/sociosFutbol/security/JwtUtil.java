package com.entradas.sociosFutbol.security;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

        public boolean validateToken(String token) {
            try {
                // Asegurarse de que el token no sea null o vacío
                if (token == null || token.trim().isEmpty()) {
                    return false;
                }

                // Si el token empieza con "Bearer ", removerlo
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                // Crear una clave segura usando HMAC-SHA256
                byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
                SecretKey key = Keys.hmacShaKeyFor(keyBytes);

                // Validar el token
                Jws<Claims> claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token);

                // Verificar que el token no haya expirado
                Date expiration = claims.getBody().getExpiration();
                if (expiration != null && expiration.before(new Date())) {
                    return false;
                }

                return true;
            } catch (SecurityException e) {
                logger.error("Invalid JWT signature: {}", e.getMessage());
                return false;
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
                return false;
            } catch (ExpiredJwtException e) {
                logger.error("JWT token is expired: {}", e.getMessage());
                return false;
            } catch (UnsupportedJwtException e) {
                logger.error("JWT token is unsupported: {}", e.getMessage());
                return false;
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty: {}", e.getMessage());
                return false;
            } catch (Exception e) {
                logger.error("Error validating JWT token: {}", e.getMessage());
                return false;
            }
        }

        // Método para generar un token (ejemplo de uso)
        public String generateToken(String username) {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                    .signWith(key)
                    .compact();
        }
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            logger.error("Error extracting username: {}", e.getMessage());
            return null;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostConstruct
    public void init() {
        // Verificar la configuración al iniciar
        try {
            String testToken = generateToken("test");
            boolean isValid = validateToken(testToken);
            logger.info("JWT configuration test: {}", isValid ? "SUCCESS" : "FAILED");
        } catch (Exception e) {
            logger.error("JWT configuration test failed: {}", e.getMessage());
        }
    }
}
