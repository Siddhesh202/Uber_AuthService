package com.example.authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    private String createToken(Map<String, Object> payload, String name) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry * 1000L);

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(name)
                .signWith(getSignInKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private Boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = extractSubject(token);
        return (userEmailFetchedFromToken.equals(email)) && !isTokenExpired(token);
    }

    private String extractPayload(String token, String payloadKey) {
        Claims claim = extractAllClaims(token);
        return (String) claim.get(payloadKey);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("phoneNumber", "999999999");
        mp.put("email", "a@b.com");
        String result = createToken(mp, "Siddhesh");
        System.out.println("Generated token is : " + result);
        System.out.println(extractPayload(result, "phoneNumber"));
        System.out.println(extractPayload(result, "email"));
        System.out.println(extractSubject(result));
    }
}
