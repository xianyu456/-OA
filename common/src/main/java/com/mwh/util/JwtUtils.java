package com.mwh.util;

import com.mwh.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author admin
 * @Description jwt工具类
 * @Date 2026/05/12/08:45
 * @Version 1.0
 */
@Component
public class JwtUtils {
    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 获取签名密钥（适配 0.12.5）
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Token（适配 0.12.5）
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)                    // 旧版: setSubject()
                .claim("role", role)
                .issuedAt(new Date())                 // 旧版: setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))  // 旧版: setExpiration()
                .signWith(getSigningKey())            // 旧版: signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 验证 Token（适配 0.12.5）
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())      // 旧版: setSigningKey()
                    .build()
                    .parseSignedClaims(token);        // 旧版: parseClaimsJws()
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析 Token 获取 Claims（适配 0.12.5）
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())          // 旧版: setSigningKey()
                .build()
                .parseSignedClaims(token)             // 旧版: parseClaimsJws()
                .getPayload();                        // 旧版: getBody()
    }

    /**
     * 获取用户名
     */
    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 获取角色
     */
    public String getRole(String token) {
        return parseToken(token).get("role", String.class);
    }
}
