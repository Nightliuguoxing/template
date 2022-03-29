package com.example.template.Jwt;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-28 23:10
 * @Description:
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 相当于登录 认证
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //登录成功时，返回json格式进行提示
        String username = obtainUsername(request);
        String jwt = JwtUtils.createJwt(username);
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>(4);
        // 这里写死只做测试  请以实际为主
        map.put("code", "200");
        map.put("message", "登陆成功！");
        map.put("token",jwt);
        response.addHeader("Authorization",  jwt);
        response.getWriter().println(JSONUtil.parse(map));
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        //登录失败时，返回json格式进行提示
        Map<String, Object> map = new HashMap<String, Object>(4);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        PrintWriter out = response.getWriter();
        if (ex instanceof BadCredentialsException) {
            map.put("code", HttpServletResponse.SC_BAD_GATEWAY);
            map.put("message", "账号或密码错误！");
        }else {
            // 这里还有其他的 异常 。。 比如账号锁定  过期 等等。。。
            map.put("code", HttpServletResponse.SC_BAD_GATEWAY);
            map.put("message", "登陆失败！");
        }
        out.write(new ObjectMapper().writeValueAsString(map));
        response.getWriter().println(JSONUtil.parse(map));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
