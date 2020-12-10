package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * <p>
 * 用户和角色多对多关系的中间表实体类
 */
public class SbUserRole extends AbstractEntity {
    /**
     * id
     */
    private Integer id;
    /**
     * 角色
     */
    private SysRole sysRole;
    /**
     * 用户
     */
    private SysUser sysUser;

    /**
     * 无参构造方法
     */
    public SbUserRole() {
    }

    /**
     * 有参构造方法-自身属性
     */
    public SbUserRole(Integer id, SysRole sysRole, SysUser sysUser) {
        this.id = id;
        this.sysRole = sysRole;
        this.sysUser = sysUser;
    }

    /**
     * 有参构造方法-所有属性
     */
    public SbUserRole(String remark, String remark1,
                      String remark2, String remark3,
                      Timestamp createdDate, String createdBy,
                      Timestamp updatedDate, String updatedBy,
                      Boolean valid, Integer id,
                      SysRole sysRole, SysUser sysUser) {
        super(remark, remark1, remark2, remark3, createdDate, createdBy, updatedDate, updatedBy, valid);
        this.id = id;
        this.sysRole = sysRole;
        this.sysUser = sysUser;
    }

    /**
     * @return 属性名=属性值
     */
    @Override
    public String toString() {
        return "SbUserRole{" +
                "id=" + id +
                ", sysRole=" + sysRole +
                ", sysUser=" + sysUser +
                '}';
    }

    /**
     * getter/setter
     */
    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
