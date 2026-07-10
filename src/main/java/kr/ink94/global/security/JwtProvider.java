package kr.ink94.global.security;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

  private static final String SECRET_KEY_STRING =  "dgehrm_final_web_service_secret_key_jwt_provider_ink94_32bytes_required";

  private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

  private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 24 hour in milliseconds

  public String generateToken(Long id, String memberId, String username, String role) {
    Date now =  new Date();
    Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
    return Jwts.builder()
        .subject(memberId)
        .claim("id", id)
        .claim("username", username)
        .claim("role", role)
        .issuedAt(now)
        .expiration(expirationDate)
        .signWith(secretKey)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Claims getClaims(String token) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
  
}
