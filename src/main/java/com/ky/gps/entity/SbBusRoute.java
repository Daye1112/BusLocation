package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * 校车与路线对应的实体类
 */
public class SbBusRoute extends AbstractEntity {

    /**
     * id
     */
    private Integer id;
    /**
     * 校车
     */
    private SbBus sbBus;
    /**
     * 路线
     */
    private SbRoute sbRoute;
    /**
     * 星期
     */
    private String sbbrWeek;
    /**
     * 开始时间
     */
    private String sbbrStartTime;
    /**
     * 结束时间
     */
    private String sbbrEndTime;

    /**
     * 无参方法
     */
    public SbBusRoute() {
    }

    /**
     * 有参方法-自身属性
     */
    public SbBusRoute(Integer id, SbBus sbBus,
                      SbRoute sbRoute, String sbbrWeek,
                      String sbbrStartTime, String sbbrEndTime) {
        this.id = id;
        this.sbBus = sbBus;
        this.sbRoute = sbRoute;
        this.sbbrWeek = sbbrWeek;
        this.sbbrStartTime = sbbrStartTime;
        this.sbbrEndTime = sbbrEndTime;
    }

    /**
     * 有参方法-所有属性
     */
    public SbBusRoute(String remark, String remark1,
                      String remark2, String remark3,
                      Timestamp createdDate, String createdBy,
                      Timestamp updatedDate, String updatedBy,
                      Boolean valid, Integer id, SbBus sbBus,
                      SbRoute sbRoute, String sbbrWeek,
                      String sbbrStartTime, String sbbrEndTime) {
        super(remark, remark1,
                remark2, remark3,
                createdDate, createdBy,
                updatedDate, updatedBy,
                valid);
        this.id = id;
        this.sbBus = sbBus;
        this.sbRoute = sbRoute;
        this.sbbrWeek = sbbrWeek;
        this.sbbrStartTime = sbbrStartTime;
        this.sbbrEndTime = sbbrEndTime;
    }

    /**
     * 重写toString
     *
     * @return 属性名+属性值
     */
    @Override
    public String toString() {
        return "SbBusRoute{" +
                "id=" + id +
                ", sbBus=" + sbBus +
                ", sbRoute=" + sbRoute +
                ", sbbrWeek='" + sbbrWeek + '\'' +
                ", sbbrStartTime='" + sbbrStartTime + '\'' +
                ", sbbrEndTime='" + sbbrEndTime + '\'' +
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

    public SbBus getSbBus() {
        return sbBus;
    }

    public void setSbBus(SbBus sbBus) {
        this.sbBus = sbBus;
    }

    public SbRoute getSbRoute() {
        return sbRoute;
    }

    public void setSbRoute(SbRoute sbRoute) {
        this.sbRoute = sbRoute;
    }

    public String getSbbrWeek() {
        return sbbrWeek;
    }

    public void setSbbrWeek(String sbbrWeek) {
        this.sbbrWeek = sbbrWeek;
    }

    public String getSbbrStartTime() {
        return sbbrStartTime;
    }

    public void setSbbrStartTime(String sbbrStartTime) {
        this.sbbrStartTime = sbbrStartTime;
    }

    public String getSbbrEndTime() {
        return sbbrEndTime;
    }

    public void setSbbrEndTime(String sbbrEndTime) {
        this.sbbrEndTime = sbbrEndTime;
    }
}
