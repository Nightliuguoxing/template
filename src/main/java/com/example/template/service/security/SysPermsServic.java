package com.example.template.service.security;

import com.example.template.dao.security.SysPermsDao;
import com.example.template.entity.security.SysPerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 22:29
 * @Description:
 */
@Service
public class SysPermsServic {

    @Autowired
    private SysPermsDao permsDao;

    public SysPerms findByPermsId(String id){
        return permsDao.findById(id).orElse(null);
    }

    public List<SysPerms> findByRoleId(String id){
        return permsDao.findByRoleId(id);
    }

}
