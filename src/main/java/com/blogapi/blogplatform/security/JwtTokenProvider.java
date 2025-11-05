package com.blogapi.blogplatform.security;

import com.blogapi.blogplatform.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret}") // Injects the value from application.properties
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private String jwtExpirationInMs;

    private SecretKey getSigningKey() {
        /* Converts the JWT Secret key into base64-encoded secret key */
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method to generate a token
    public String generateToken(Authentication authentication) {
        // Get the principal (the user) from the auth
        User userPrincipal = (User) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = Date.from(Instant.now().plusMillis(Long.parseLong(this.jwtExpirationInMs)));

        // Build the JWT
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    // Method to get username from token
    public String getUsernameFromToken(String token) {
        // Parse the token and get the claims
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // Method to validate a token
    public Boolean validateToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token!");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token!");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token!");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty!");
        }

        return false;
    }

}
