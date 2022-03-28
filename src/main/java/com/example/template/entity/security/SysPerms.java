package com.example.template.entity.security;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 17:44
 * @Description:
 */
@Entity
@Table(name = "sys_perms")
public class SysPerms implements Serializable {

    private static final long serialVersionUID = 3612115236534816222L;

    @Id
    private String id;

    private String permsname;

    private String status;

    private String url;

    private String rid;

    @Transient
    private List permissions;

    public List getPermissions() {
        return Arrays.asList(this.permsname.trim().split(","));
    }

    public void setPermissions(List permission) {
        this.permissions = permission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermsname() {
        return permsname;
    }

    public void setPermsname(String permsname) {
        this.permsname = permsname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

}
