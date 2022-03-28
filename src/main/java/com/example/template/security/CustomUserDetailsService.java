package com.example.template.security;

import com.example.template.entity.security.SysRole;
import com.example.template.entity.security.SysUser;
import com.example.template.entity.security.SysUserRole;
import com.example.template.service.security.SysRoleService;
import com.example.template.service.security.SysUserRoleService;
import com.example.template.service.security.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 20:49
 * @Description:
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        SysUser user = userService.findByUsername(username);

        // 判断用户是否存在
        if(user == null) {
            throw new InternalAuthenticationServiceException("用户名不存在");
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.findByUserRole(user.getId());
        for (SysUserRole userRole : userRoles) {
            SysRole role = roleService.findByRoleId(userRole.getRid());
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }

        // 返回UserDetails实现类
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
