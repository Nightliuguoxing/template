package com.example.template.dao.security;

import com.example.template.dao.BaseDao;
import com.example.template.entity.security.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022/3/23 20:25
 * @Description:
 */
@Repository
public interface SysRoleDao extends BaseDao<SysRole, String> {

    @Query(value = "SELECT r FROM SysRole r WHERE r.rolename = ?1")
    public SysRole findByRolename(String rolename);

}
