package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * 角色权限中间表
 *
 * @author Daye
 */
public class SbRoleAuthority extends AbstractEntity {
    /**
     * 自增id
     */
    private Integer id;
    private SysRole sysRole;
    private SysAuthority sysAuthority;

    public SbRoleAuthority() {
    }

    public SbRoleAuthority(Integer id, SysRole sysRole, SysAuthority sysAuthority) {
        this.id = id;
        this.sysRole = sysRole;
        this.sysAuthority = sysAuthority;
    }

    public SbRoleAuthority(String remark, String remark1,
                           String remark2, String remark3,
                           Timestamp createdDate, String createdBy,
                           Timestamp updatedDate, String updatedBy,
                           Boolean valid, Integer id,
                           SysRole sysRole, SysAuthority sysAuthority) {
        super(remark, remark1,
                remark2, remark3,
                createdDate, createdBy,
                updatedDate, updatedBy,
                valid);
        this.id = id;
        this.sysRole = sysRole;
        this.sysAuthority = sysAuthority;
    }

    @Override
    public String toString() {
        return "SbRoleAuthority{" +
                "id=" + id +
                ", sysRole=" + sysRole +
                ", sysAuthority=" + sysAuthority +
                "} " + super.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysAuthority getSysAuthority() {
        return sysAuthority;
    }

    public void setSysAuthority(SysAuthority sysAuthority) {
        this.sysAuthority = sysAuthority;
    }
}