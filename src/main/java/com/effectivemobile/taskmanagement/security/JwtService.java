package com.effectivemobile.taskmanagement.security;

import com.effectivemobile.taskmanagement.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.Jwts.parser;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service

public class JwtService {
  @Value("${JWT_SECRET}")
  private String jwtSigningKey;

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String generateToken(Account account) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("email", account.getEmail());
    return generateToken(claims, account);
  }

  public boolean isTokenValid(String token, Account account) {
    final String email = extractEmail(token);
    return (email.equals(account.getEmail())) && !isTokenExpired(token);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  public String generateToken(Map<String, Object> extraClaims, Account account) {
    int jwtExpirationMs = 3000000 + 60 * 24;
    return Jwts.builder().claims(extraClaims).subject(account.getEmail())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
