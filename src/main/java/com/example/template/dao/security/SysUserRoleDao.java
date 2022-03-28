package com.example.template.dao.security;

import com.example.template.dao.BaseDao;
import com.example.template.entity.security.SysUserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022/3/23 20:27
 * @Description:
 */
@Repository
public interface SysUserRoleDao extends BaseDao<SysUserRole, String> {

    @Query(value = "SELECT ur FROM SysUserRole ur WHERE ur.uid = ?1")
    public List<SysUserRole> findByUserId(String id);
}
