package com.example.template.service.security;

import com.example.template.dao.security.SysRoleDao;
import com.example.template.entity.security.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 20:37
 * @Description:
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    public SysRole findByRoleId(String id){
        return sysRoleDao.findById(id).orElse(null);
    }

    public SysRole findByRolename(String rolename){
        return sysRoleDao.findByRolename(rolename);
    }
}
