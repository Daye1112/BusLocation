package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * 站点信息实体类
 */
public class SbStation extends AbstractEntity {

    /**
     * id
     */
    private Integer id;
    /**
     * 站点名
     */
    private String sbsStation;
    /**
     * 经度
     */
    private Double sbsLongitude;
    /**
     * 纬度
     */
    private Double sbsLatitude;
    /**
     * 站点描述
     */
    private String sbsDesc;

    /**
     * 无参方法
     */
    public SbStation() {
    }

    /**
     * 有参方法-自身方法
     */
    public SbStation(Integer id, String sbsStation,
                     Double sbsLongitude, Double sbsLatitude,
                     String sbsDesc) {
        this.id = id;
        this.sbsStation = sbsStation;
        this.sbsLongitude = sbsLongitude;
        this.sbsLatitude = sbsLatitude;
        this.sbsDesc = sbsDesc;
    }

    /**
     * 有参方法-所有属性
     */
    public SbStation(String remark, String remark1,
                     String remark2, String remark3,
                     Timestamp createdDate, String createdBy,
                     Timestamp updatedDate, String updatedBy,
                     Boolean valid, Integer id,
                     String sbsStation, Double sbsLongitude,
                     Double sbsLatitude, String sbsDesc) {
        super(remark, remark1,
                remark2, remark3,
                createdDate, createdBy,
                updatedDate, updatedBy,
                valid);
        this.id = id;
        this.sbsStation = sbsStation;
        this.sbsLongitude = sbsLongitude;
        this.sbsLatitude = sbsLatitude;
        this.sbsDesc = sbsDesc;
    }

    @Override
    public String toString() {
        return "SbStation{" +
                "id=" + id +
                ", sbsStation='" + sbsStation + '\'' +
                ", sbsLongitude=" + sbsLongitude +
                ", sbsLatitude=" + sbsLatitude +
                ", sbsDesc='" + sbsDesc + '\'' +
                "} " + super.toString();
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

    public String getSbsStation() {
        return sbsStation;
    }

    public void setSbsStation(String sbsStation) {
        this.sbsStation = sbsStation;
    }

    public double getSbsLongitude() {
        return sbsLongitude;
    }

    public void setSbsLongitude(Double sbsLongitude) {
        this.sbsLongitude = sbsLongitude;
    }

    public Double getSbsLatitude() {
        return sbsLatitude;
    }

    public void setSbsLatitude(Double sbsLatitude) {
        this.sbsLatitude = sbsLatitude;
    }

    public String getSbsDesc() {
        return sbsDesc;
    }

    public void setSbsDesc(String sbsDesc) {
        this.sbsDesc = sbsDesc;
    }
}
