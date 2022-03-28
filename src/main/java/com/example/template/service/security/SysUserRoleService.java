package com.example.template.service.security;

import com.example.template.dao.security.SysUserRoleDao;
import com.example.template.entity.security.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 20:39
 * @Description:
 */
@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    public List<SysUserRole> findByUserRole(String id){
        return sysUserRoleDao.findByUserId(id);
    }

}
