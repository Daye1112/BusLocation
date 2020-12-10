package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * GPS设备实体类
 */
public class SbGps extends AbstractEntity {
    /**
     * 设备发送的GPS信号id
     */
    private String id;
    /**
     * 对应的校车
     */
    private SbBus sbBus;
    /**
     * GPS编号
     */
    private String sbgNumber;
    /**
     * 型号
     */
    private String sbgType;
    /**
     * 厂家
     */
    private String sbgFactoryName;

    /**
     * 无参方法
     */
    public SbGps() {
    }

    /**
     * 有参方法-自身属性
     */
    public SbGps(String id, SbBus sbBus,
                 String sbgNumber,
                 String sbgType, String sbgFactoryName) {
        this.id = id;
        this.sbBus = sbBus;
        this.sbgNumber = sbgNumber;
        this.sbgType = sbgType;
        this.sbgFactoryName = sbgFactoryName;
    }

    /**
     * 有参方法-自身属性
     */
    public SbGps(String remark, String remark1,
                 String remark2, String remark3,
                 Timestamp createdDate, String createdBy,
                 Timestamp updatedDate, String updatedBy,
                 Boolean valid, String id, SbBus sbBus,
                 String sbgNumber, String sbgType,
                 String sbgFactoryName) {
        super(remark, remark1,
                remark2, remark3,
                createdDate, createdBy,
                updatedDate, updatedBy,
                valid);
        this.id = id;
        this.sbBus = sbBus;
        this.sbgNumber = sbgNumber;
        this.sbgType = sbgType;
        this.sbgFactoryName = sbgFactoryName;
    }

    /**
     * 有参方法-自身id
     */
    public SbGps(String id) {
        this.id = id;
    }

    /**
     * 有参方法-所有属性
     */
    @Override
    public String toString() {
        return "SbGps{" +
                "id=" + id +
                ", sbBus=" + sbBus +
                ", sbgType='" + sbgType + '\'' +
                ", sbgFactoryName='" + sbgFactoryName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SbBus getSbBus() {
        return sbBus;
    }

    public void setSbBus(SbBus sbBus) {
        this.sbBus = sbBus;
    }

    public String getSbgNumber() {
        return sbgNumber;
    }

    public void setSbgNumber(String sbgNumber) {
        this.sbgNumber = sbgNumber;
    }

    public String getSbgType() {
        return sbgType;
    }

    public void setSbgType(String sbgType) {
        this.sbgType = sbgType;
    }

    public String getSbgFactoryName() {
        return sbgFactoryName;
    }

    public void setSbgFactoryName(String sbgFactoryName) {
        this.sbgFactoryName = sbgFactoryName;
    }
}
