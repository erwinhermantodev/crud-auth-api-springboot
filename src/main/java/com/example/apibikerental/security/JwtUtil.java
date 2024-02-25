package com.example.apibikerental.security;

import com.example.apibikerental.controller.AdminController;
import com.example.apibikerental.model.User;
import com.example.apibikerental.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    byte[] secretKeyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    private Key getSigningKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a secure key
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("user_id", user.getUserID())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKeyBytes))
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            // Log or handle the exception
            logger.error("error parse token: " + e.getMessage());
            return null;
        }
    }

    public Claims validateToken(String token) {
        try {
            Claims claimsJws = parseToken(token);
            logger.info("parse token: " + claimsJws);
            return claimsJws;
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
            return null;
        }
    }
}

