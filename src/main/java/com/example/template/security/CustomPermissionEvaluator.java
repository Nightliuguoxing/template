package com.example.template.security;

import com.example.template.entity.security.SysPerms;
import com.example.template.service.security.SysPermsServic;
import com.example.template.service.security.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 22:54
 * @Description:
 */
@Service
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysPermsServic permsServic;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        // 获得loadUserByUsername()方法的结果
        User user = (User) authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        // 遍历用户所有角色
        for(GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            String roleId = roleService.findByRolename(roleName).getId();
            // 得到角色所有的权限
            List<SysPerms> permissionList = permsServic.findByRoleId(roleId);

            // 遍历permissionList
            for(SysPerms perms : permissionList) {
                // 获取权限集
                List permissions = perms.getPermissions();
                // 如果访问的Url和权限用户符合的话，返回true
                if(targetUrl.equals(perms.getUrl())
                        && permissions.contains(targetPermission)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

}
