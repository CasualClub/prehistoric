package com.casualclub.prehistoric.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class JWTUtils {

    private static final String secret = "abcdefghijkmnloptrstuvwxyz";
    private static final Duration expiration = Duration.ofSeconds(3600);
    private static final String AUTHORITIES_CLAIMS = "authorities_";

    /**
     * 创建JWT Token
     * @param id 用户id
     * @param username 用户名
     * @param authorities 权限
     * @return token
     */
    private static String createToken(String id, String username, Collection<String> authorities) {
        final Instant createInstant = Instant.now();
        final Date expireDate = Date.from(createInstant.plus(expiration));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8)))
                .claim(AUTHORITIES_CLAIMS, String.join(",", authorities))
                .setId(id)
                .setSubject(username)
                .setIssuedAt(Date.from(createInstant))
                .setExpiration(expireDate)
                .compact();
    }

    public static String getId(String token) {
        return getAllClaimsFromToken(token).getId();
    }

    /**
     * 获取用户名
     * @param token token
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * 获取用户登录授权
     * @param token token
     * @return 登录授权
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getAllClaimsFromToken(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String userName = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    /**
     * 获取权限
     * @param claims claims
     * @return 权限列表
     */
    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String role = (String) claims.get(AUTHORITIES_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * 获取过期时间
     * @param token token
     * @return 过期时间
     */
    public static Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    /**
     * token有效性
     * @param token token
     * @return boolean
     */
    public static Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * token是否过期
     * @param token token
     * @return 是否
     */
    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(Date.from(Instant.now()));
    }

    /**
     * Get Claims
     * @param token token
     * @return claims
     */
    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
    }

}
