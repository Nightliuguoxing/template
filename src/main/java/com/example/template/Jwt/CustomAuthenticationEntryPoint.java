package com.example.template.Jwt;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-28 23:17
 * @Description: 当未登录或者token失效访问接口时，自定义的返回结果
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>(4);
        // 这里写死只做测试  请以实际为主
        map.put("code", HttpServletResponse.SC_BAD_GATEWAY);
        map.put("message", "请登录！");
        response.getWriter().println(JSONUtil.parse(map));
        response.getWriter().flush();
    }
}
