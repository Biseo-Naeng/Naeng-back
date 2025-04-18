package com.naeng_biseo.naeng_biseo.security;

import com.naeng_biseo.naeng_biseo.dto.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final SecretKey key;
    private final StringRedisTemplate redisTemplate;

    public JwtUtil(@Value("${jwt.secret}") String secretKey,
                   StringRedisTemplate redisTemplate) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisTemplate = redisTemplate;
    }

    // JWT 토큰 생성
    public JwtToken generateToken(Authentication authentication) {
        String authority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        long now = System.currentTimeMillis();

        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 60 * 24); // 24시간
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authority)
                .setIssuedAt(new Date(now))
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000L * 60 * 60 * 24 * 7)) // 7일
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT에서 Authentication 객체 추출
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        String authClaim = claims.get("auth", String.class);
        if (authClaim == null || authClaim.trim().isEmpty()) {
            throw new RuntimeException("Invalid access token: no authority information");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authClaim);

        UserDetails principal = new User(claims.getSubject(), "", Collections.singletonList(authority));

        return new UsernamePasswordAuthenticationToken(principal, accessToken, principal.getAuthorities());
    }

    // JWT 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            Boolean hasKey = redisTemplate.hasKey("jwt:" + token);
            return Boolean.TRUE.equals(hasKey);
        } catch (Exception e) {
            return false;
        }
    }


    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}