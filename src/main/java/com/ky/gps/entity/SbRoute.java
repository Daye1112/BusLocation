package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * 路线表对应的实体类
 */
public class SbRoute extends AbstractEntity {

    /**
     * id
     */
    private Integer id;
    /**
     * 路线名
     */
    private String sbrRouteName;
    /**
     * 始发站
     */
    private String sbrStartStation;
    /**
     * 终点站
     */
    private String sbrEndStation;
    /**
     * 终点站
     */
    private String sbrDepartTime;
    /**
     * 路线信息
     */
    private String sbrDesc;

    /**
     * 无参方法
     */
    public SbRoute() {
    }

    /**
     * 有参方法-自身属性
     */
    public SbRoute(Integer id, String sbrRouteName,
                   String sbrStartStation, String sbrEndStation,
                   String sbrDepartTime, String sbrDesc) {
        this.id = id;
        this.sbrRouteName = sbrRouteName;
        this.sbrStartStation = sbrStartStation;
        this.sbrEndStation = sbrEndStation;
        this.sbrDepartTime = sbrDepartTime;
        this.sbrDesc = sbrDesc;
    }

    /**
     * 有参方法-所有属性
     */
    public SbRoute(String remark, String remark1,
                   String remark2, String remark3,
                   Timestamp createdDate, String createdBy,
                   Timestamp updatedDate, String updatedBy,
                   Boolean valid, Integer id,
                   String sbrRouteName, String sbrStartStation,
                   String sbrEndStation, String sbrDepartTime,
                   String sbrDesc) {
        super(remark, remark1, remark2, remark3, createdDate, createdBy, updatedDate, updatedBy, valid);
        this.id = id;
        this.sbrRouteName = sbrRouteName;
        this.sbrStartStation = sbrStartStation;
        this.sbrEndStation = sbrEndStation;
        this.sbrDepartTime = sbrDepartTime;
        this.sbrDesc = sbrDesc;
    }

    /**
     * 重写toString
     *
     * @return 属性名+属性值
     */
    @Override
    public String toString() {
        return "SbRoute{" +
                "id=" + id +
                ", sbrRouteName='" + sbrRouteName + '\'' +
                ", sbrStartStation='" + sbrStartStation + '\'' +
                ", sbrEndStation='" + sbrEndStation + '\'' +
                ", sbrDepartTime='" + sbrDepartTime + '\'' +
                ", sbrDesc='" + sbrDesc + '\'' +
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

    public String getSbrRouteName() {
        return sbrRouteName;
    }

    public void setSbrRouteName(String sbrRouteName) {
        this.sbrRouteName = sbrRouteName;
    }

    public String getSbrStartStation() {
        return sbrStartStation;
    }

    public void setSbrStartStation(String sbrStartStation) {
        this.sbrStartStation = sbrStartStation;
    }

    public String getSbrEndStation() {
        return sbrEndStation;
    }

    public void setSbrEndStation(String sbrEndStation) {
        this.sbrEndStation = sbrEndStation;
    }

    public String getSbrDepartTime() {
        return sbrDepartTime;
    }

    public void setSbrDepartTime(String sbrDepartTime) {
        this.sbrDepartTime = sbrDepartTime;
    }

    public String getSbrDesc() {
        return sbrDesc;
    }

    public void setSbrDesc(String sbrDesc) {
        this.sbrDesc = sbrDesc;
    }
}
