package com.study.flashcardaibackend.service.user;

import com.study.flashcardaibackend.constant.ErrorConstants;
import com.study.flashcardaibackend.constant.JwtConstants;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {
    Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String userIdentity) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userIdentity)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();

    }

    public String extractEmail(String token) {
        Date expiredAt = extractClaim(token, Claims::getExpiration);
        if (expiredAt.before(new Date())) {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.JWT_TOKEN_IS_EXPIRED);
        }
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
        } catch (Exception e) {
            logger.warn("Exception when extract user token: {}", e.getMessage());
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.JWT_TOKEN_IS_INVALID);
        }

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}