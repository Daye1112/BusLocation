package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * 站点和路线对应实体类
 */
public class SbRouteStation extends AbstractEntity {

    /**
     * id
     */
    private Integer id;
    /**
     * 路线id所对应的实体类
     */
    private SbRoute sbRoute;
    /**
     * 站点id所对应的实体类
     */
    private SbStation sbStation;
    /**
     * 发车时间
     */
    private String sbsDepartTime;

    /**
     * 无参方法
     */
    public SbRouteStation() {
    }

    /**
     * 有参方法-自身属性
     */
    public SbRouteStation(Integer id, SbRoute sbRoute, SbStation sbStation, String sbsDepartTime) {
        this.id = id;
        this.sbRoute = sbRoute;
        this.sbStation = sbStation;
        this.sbsDepartTime = sbsDepartTime;
    }

    /**
     * 有参方法-所有属性
     */
    public SbRouteStation(String remark, String remark1,
                          String remark2, String remark3,
                          Timestamp createdDate, String createdBy,
                          Timestamp updatedDate, String updatedBy,
                          Boolean valid, Integer id,
                          SbRoute sbRoute, SbStation sbStation,
                          String sbsDepartTime) {
        super(remark, remark1, remark2, remark3, createdDate, createdBy, updatedDate, updatedBy, valid);
        this.id = id;
        this.sbRoute = sbRoute;
        this.sbStation = sbStation;
        this.sbsDepartTime = sbsDepartTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * getter/stter
     */

    public SbRoute getSbRoute() {
        return sbRoute;
    }

    public void setSbRoute(SbRoute sbRoute) {
        this.sbRoute = sbRoute;
    }

    public SbStation getSbStation() {
        return sbStation;
    }

    public void setSbStation(SbStation sbStation) {
        this.sbStation = sbStation;
    }

    public String getSbsDepartTime() {
        return sbsDepartTime;
    }

    public void setSbsDepartTime(String sbsDepartTime) {
        this.sbsDepartTime = sbsDepartTime;
    }
}
