package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * <p>
 * 用户与校车的多对多表的实体类
 */
public class SbUserBus extends AbstractEntity {

    /**
     * id
     */
    private Integer id;
    /**
     * 用户对象
     */
    private SysUser sysUser;
    /**
     * 路线对象
     */
    private SbRoute sbRoute;

    /**
     * 无参构造方法
     */
    public SbUserBus() {
    }

    /**
     * 有参构造方法-id
     */
    public SbUserBus(Integer id) {
        this.id = id;
    }

    /**
     * 有参构造方法-自身属性
     */
    public SbUserBus(Integer id, SysUser sysUser, SbRoute sbRoute) {
        this.id = id;
        this.sysUser = sysUser;
        this.sbRoute = sbRoute;
    }

    /**
     * 有参构造方法-所有属性
     */
    public SbUserBus(String remark, String remark1,
                     String remark2, String remark3,
                     Timestamp createdDate, String createdBy,
                     Timestamp updatedDate, String updatedBy,
                     Boolean valid, Integer id,
                     SysUser sysUser, SbRoute sbRoute) {
        super(remark, remark1, remark2, remark3, createdDate, createdBy, updatedDate, updatedBy, valid);
        this.id = id;
        this.sysUser = sysUser;
        this.sbRoute = sbRoute;
    }

    /**
     * toString方法
     *
     * @return 属性名=属性值
     */
    @Override
    public String toString() {
        return "SbUserBus{" +
                "id=" + id +
                ", sysUser=" + sysUser +
                ", sbRoute=" + sbRoute +
                '}';
    }

    /**
     * getter/setter
     */
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

    public SbRoute getSbRoute() {
        return sbRoute;
    }

    public void setSbRoute(SbRoute sbRoute) {
        this.sbRoute = sbRoute;
    }
}
