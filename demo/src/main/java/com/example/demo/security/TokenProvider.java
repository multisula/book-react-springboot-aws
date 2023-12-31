package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
  private static final String SECRET_KEY = "ModeSVdX82SWpzTRdS5nYrHAB0E0+itPl2z/Qz9iG5In0iPf3L0su3eh+gtBT/ZpgHTLj6tn2zm7ModeSVdX82SWpzTRdS5nYrHAB0E0+itPl2z/Qz9iG5In0iPf3L0su3eh+gtBT/ZpgHTLj6tn2zm7";

  public String create(UserEntity userEntity) {
    Date expiryDate = Date.from(
        Instant.now()
            .plus(1, ChronoUnit.DAYS));

    /*
    { // header
      "alg":"HS512"
    }.
    { // payload
      "sub":"40288093784915d201784916a40c0001",
      "iss": "demo app",
      "iat":1595733657,
      "exp":15965976567
    }.
    // SECRET_KEY를 이용해 서명한 부분
    Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNrXdJJNWkRv50_17bPLQPwhMobT4vBOG6Q3JYjhDrKFlBSaUxZOg
     */

    // JWT Token 생성
    return Jwts.builder()
        // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        // payload에 들어갈 내용
        .setSubject(userEntity.getId())
        .setIssuer("demo app")
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .compact();
  }

  public String validateAndGetUserId(String token) {
    // parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
    // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
    // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
    // 그중 우리는 userId가 필요하므로 getBody를 부른다.
    Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }
}
