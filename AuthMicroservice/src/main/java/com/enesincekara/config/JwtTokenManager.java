package com.enesincekara.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class JwtTokenManager {

    @Value("${auth.jwt.secret-key}")
    private String secretKey;
    @Value("${auth.jwt.issuer}")
    private String issuer;

    private final Long EXPIRATION_TIME = 1000L * 60 * 30;


    public Optional<String> createToken(UUID id){
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            String token = Jwts.builder()
                    .setIssuer(issuer)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .claim("authId",id.toString())
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
            return Optional.of(token);
        }catch (Exception e){
            return Optional.empty();
        }
    }

}
