package com.arzuahmed.ticketingsystem.security.jwt;

import com.arzuahmed.ticketingsystem.security.UserSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
    @Service
    public class JwtService {

        @Value("${jwt.secret:default-secret-key-for-jwt-signature-that-is-at-least-32-bytes}")
        private String secretKey;

        @Value("${jwt.access-token.expiration:300000}")
        private long accessTokenExpiration;

        @Value("${jwt.refresh-token.expiration:3600000}")
        private long refreshTokenExpiration;

        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        public String generateAccessToken(UserDetails userDetails) {
            return generateAccessToken(new HashMap<>(), userDetails);
        }

        public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
            return buildToken(extraClaims, userDetails, accessTokenExpiration);
        }

        public String generateRefreshToken(UserDetails userDetails) {
            return buildToken(new HashMap<>(), userDetails, refreshTokenExpiration);
        }

        private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
            if (userDetails instanceof UserSecurity securityUser) {
                extraClaims.put("userId", securityUser.getUser().getId());
                extraClaims.put("userEmail", securityUser.getUsername());

                List<String> roles = securityUser.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                extraClaims.put("roles", roles);

            }

            return Jwts
                    .builder()
                    .claims(extraClaims)
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSigningKey(), Jwts.SIG.HS256)
                    .compact();
        }

        public boolean isTokenValid(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        private Claims extractAllClaims(String token) {
            return Jwts
                    .parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

        private SecretKey getSigningKey() {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }


    }

