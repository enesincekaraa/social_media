package com.enesincekara.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
public class JwtTokenManager {
    @Value("${auth.jwt.secret-key}")
    private String secretKey;



    public Optional<UUID> getIdFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();


            String authIdStr = claims.get("authId", String.class);
            return Optional.of(UUID.fromString(authIdStr));

        } catch (Exception ex) {

            return Optional.empty();
        }
    }

    public Optional<String> getRoleFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();


            String role = claims.get("role", String.class);
            return Optional.of(role);

        } catch (Exception ex) {

            return Optional.empty();
        }
    }


}
