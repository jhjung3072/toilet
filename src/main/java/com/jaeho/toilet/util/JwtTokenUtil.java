package com.jaeho.toilet.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private static final long JWT_TOKEN_VALIDITY = 5*60*60;
    @Value("${jwt.secret}")
    private String secret;

    public  String generateToken(String username) {
        Map<String ,Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String getUserNameFromToken(String jwtToken){
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T>claimsResolver){
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        String username = getUserNameFromToken(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        Date expiration = getExpirationDateFromToken(jwtToken);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken,Claims::getExpiration);
    }
}
