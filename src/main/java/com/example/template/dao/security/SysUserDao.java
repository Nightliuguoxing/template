package com.example.template.dao.security;

import com.example.template.dao.BaseDao;
import com.example.template.entity.security.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022/3/23 20:20
 * @Description:
 */
@Repository
public interface SysUserDao extends BaseDao<SysUser, String> {

    @Query(value = "SELECT u FROM SysUser u WHERE u.username = ?1")
    public SysUser findByUsername(String username);

}
