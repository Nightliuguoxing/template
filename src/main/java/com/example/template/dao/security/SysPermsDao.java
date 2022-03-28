package com.example.template.dao.security;

import com.example.template.dao.BaseDao;
import com.example.template.entity.security.SysPerms;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 22:06
 * @Description:
 */
@Repository
public interface SysPermsDao extends BaseDao<SysPerms, String> {

    @Query(value = "SELECT p FROM SysPerms p WHERE p.rid = ?1")
    public List<SysPerms> findByRoleId(String id);

}
