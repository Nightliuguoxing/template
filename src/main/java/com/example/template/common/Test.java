package com.example.template.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 21:24
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        String token = Jwts.builder()
                .setSubject("Lucifer")
                .claim("authorities", "admin")
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, "tmax")
                .compact();


        try {
            //解析token
            Claims claims = Jwts.parser()
                    .setSigningKey("tmax")
                    .parseClaimsJws(token).getBody();
            System.out.println(claims);
            //获取用户名
            String username = claims.getSubject();
            System.out.println("username:"+username);
            //获取权限
            String authority = claims.get("authorities").toString();
            System.out.println("权限："+authority);
        } catch (ExpiredJwtException e) {
            System.out.println("jwt异常");
        } catch (Exception e){
            System.out.println("异常");
        }
    }

    private void bncoder(){
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
