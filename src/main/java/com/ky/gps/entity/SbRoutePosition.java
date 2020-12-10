package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * <p>
 * 路线行驶轨迹表实体类
 */
public class SbRoutePosition extends AbstractEntity {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 与路线1-n关系
     */
    private SbRoute sbRoute;

    /**
     * 排序序号
     */
    private Double srpIndex;

    /**
     * 经度
     */
    private Double srpLongitude;

    /**
     * 纬度
     */
    private Double srpLatitude;

    /**
     * 构造方法-无参
     */
    public SbRoutePosition() {
    }

    /**
     * 构造方法-自身参数
     */
    public SbRoutePosition(Integer id, SbRoute sbRoute,
                           Double srpIndex, Double srpLongitude,
                           Double srpLatitude) {
        this.id = id;
        this.sbRoute = sbRoute;
        this.srpIndex = srpIndex;
        this.srpLongitude = srpLongitude;
        this.srpLatitude = srpLatitude;
    }

    /**
     * 构造方法-所有参数
     */
    public SbRoutePosition(String remark, String remark1,
                           String remark2, String remark3,
                           Timestamp createdDate, String createdBy,
                           Timestamp updatedDate, String updatedBy,
                           Boolean valid, Integer id,
                           SbRoute sbRoute, Double srpIndex,
                           Double srpLongitude, Double srpLatitude) {
        super(remark, remark1, remark2, remark3, createdDate, createdBy, updatedDate, updatedBy, valid);
        this.id = id;
        this.sbRoute = sbRoute;
        this.srpIndex = srpIndex;
        this.srpLongitude = srpLongitude;
        this.srpLatitude = srpLatitude;
    }

    @Override
    public String toString() {
        return "SbRoutePosition{" +
                "id=" + id +
                ", sbRoute=" + sbRoute +
                ", srpIndex=" + srpIndex +
                ", srpLongitude=" + srpLongitude +
                ", srpLatitude=" + srpLatitude +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SbRoute getSbRoute() {
        return sbRoute;
    }

    public void setSbRoute(SbRoute sbRoute) {
        this.sbRoute = sbRoute;
    }

    public Double getSrpIndex() {
        return srpIndex;
    }

    public void setSrpIndex(Double srpIndex) {
        this.srpIndex = srpIndex;
    }

    public Double getSrpLongitude() {
        return srpLongitude;
    }

    public void setSrpLongitude(Double srpLongitude) {
        this.srpLongitude = srpLongitude;
    }

    public Double getSrpLatitude() {
        return srpLatitude;
    }

    public void setSrpLatitude(Double srpLatitude) {
        this.srpLatitude = srpLatitude;
    }

}