package com.example.template.service.security;

import com.example.template.dao.security.SysUserDao;
import com.example.template.entity.security.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 20:33
 * @Description:
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;


    public SysUser findById(String id){
        return sysUserDao.findById(id).orElse(null);
    }

    public SysUser findByUsername(String username){
        return sysUserDao.findByUsername(username);
    }

}
