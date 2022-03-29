package com.example.template.Jwt;

import cn.hutool.json.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-28 23:15
 * @Description: 当访问接口没有权限时主要实现springsecurity给我们提供的 AccessDeniedHandler接口，自定义的返回结果
 */
@Component
public class CustomAccessDeniedHandle implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        // 这里写死只做测试  请以实际为主
        Map<String, Object> map = new HashMap<>();
        map.put("code", 501);
        map.put("msg", "您没有权限");
        response.getWriter().println(JSONUtil.parse(map));
        response.getWriter().flush();
    }
}
