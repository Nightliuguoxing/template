package com.example.template.controller;

import com.example.template.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 20:42
 * @Description:
 */
@RestController
public class SysController {

    private Logger logger = LoggerFactory.getLogger(SysController.class);

    @GetMapping("/")
    public Result index() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登录用户是: " + name);
        return new Result(true, 200, "当前" + name + "成功", name);
    }

    @GetMapping("/me")
    public Result me(@AuthenticationPrincipal UserDetails userDetails) {
        return new Result(true, 200, "已查询个人信息~", userDetails);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasPermission('/admin','SELECT')")
    public Result admin() {
        return new Result(true, 200, "如果你看见这句话，说明你访问/admin路径具有SELECT权限");
    }

    @GetMapping("/admin/c")
    @PreAuthorize("hasPermission('/admin','INSERT')")
    public Result adminC() {
        return new Result(true, 200, "如果你看见这句话，说明你访问/admin路径具有CREATE权限");
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result user() {
        return new Result(true, 200, "如果你看见这句话，说明你有ROLE_USER角色");
    }

}
