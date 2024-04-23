package com.example.bootcamp2024onclass.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final SecretKey key;

    public JwtTokenProvider(@Value("${secretKey}") String secretKey) {
        this.secretKey = secretKey;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Date expiration = claimsJws.getBody().getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                throw new JwtException("Token expired");
            }
            return true;
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }
}
