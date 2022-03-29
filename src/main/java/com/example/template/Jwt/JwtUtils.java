package com.example.template.Jwt;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-28 23:20
 * @Description:
 */
public final class JwtUtils {

    private static final String ACCESS_TOKEN_SECRET = "aaaaaaaaaaaaaaaaaaa";

    private static final String JWT_PAYLOAD_USER_KEY = "user";

    private static final long expire = 1000L;

    /**
     * 加密token
     *
     * @param userInfo   载荷中的数据
     * @return JWT
     */
    public static String createJwt(Object userInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put(JWT_PAYLOAD_USER_KEY, userInfo);
        return Jwts.builder()
                .setClaims(map)
                .setId(createJTI())
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000))
                .signWith(SignatureAlgorithm.HS256,ACCESS_TOKEN_SECRET)
                .compact();
    }

    /**
     * 获取token中的载荷信息
     *
     * @param token     用户请求中的令牌
     * @return 用户信息
     */
    public static Claims parserToken(String token) {
        return Jwts.parser()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Object getUserInfo(String token){
        Claims claims = parserToken(token);
        return claims.get(JWT_PAYLOAD_USER_KEY);
    }

    private static String createJTI() {
        return new String(java.util.Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }


}
