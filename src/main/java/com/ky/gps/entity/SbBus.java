package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 * 校车实体类
 */
public class SbBus extends AbstractEntity {

    /**
     * 位置id
     */
    private Integer id;
    /**
     * 座位数
     */
    private Integer sbbSeatNum;
    /**
     * 车辆类型
     */
    private String sbbBusType;
    /**
     * 车牌号
     */
    private String sbbPlateNumber;
    /**
     * 驾驶员
     */
    private String sbbDriverName;
    /**
     * 联系电话
     */
    private String sbbDriverTel;

    /**
     * 无参构造方法
     */
    public SbBus() {
    }

    /**
     * 有参构造方法-id
     */
    public SbBus(Integer id) {
        this.id = id;
    }

    /**
     * 有参构造方法-自身属性
     */
    public SbBus(Integer id, Integer sbbSeatNum,
                 String sbbBusType, String sbbPlateNumber,
                 String sbbDriverName, String sbbDriverTel) {
        this.id = id;
        this.sbbSeatNum = sbbSeatNum;
        this.sbbBusType = sbbBusType;
        this.sbbPlateNumber = sbbPlateNumber;
        this.sbbDriverName = sbbDriverName;
        this.sbbDriverTel = sbbDriverTel;
    }

    /**
     * 有参构造方法-所有属性
     */
    public SbBus(String remark, String remark1,
                 String remark2, String remark3,
                 Timestamp createdDate, String createdBy,
                 Timestamp updatedDate, String updatedBy,
                 Boolean valid, Integer id, Integer sbbSeatNum,
                 String sbbBusType, String sbbPlateNumber,
                 String sbbDriverName, String sbbDriverTel) {
        super(remark, remark1, remark2, remark3, createdDate, createdBy, updatedDate, updatedBy, valid);
        this.id = id;
        this.sbbSeatNum = sbbSeatNum;
        this.sbbBusType = sbbBusType;
        this.sbbPlateNumber = sbbPlateNumber;
        this.sbbDriverName = sbbDriverName;
        this.sbbDriverTel = sbbDriverTel;
    }

    /**
     * 重写toString
     *
     * @return 属性名+属性值
     */
    @Override
    public String toString() {
        return "SbBus{" +
                "id=" + id +
                ", sbbSeatNum=" + sbbSeatNum +
                ", sbbBusType='" + sbbBusType + '\'' +
                ", sbbPlateNumber='" + sbbPlateNumber + '\'' +
                ", sbbDriverName='" + sbbDriverName + '\'' +
                ", sbbDriverTel='" + sbbDriverTel + '\'' +
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

    public Integer getSbbSeatNum() {
        return sbbSeatNum;
    }

    public void setSbbSeatNum(Integer sbbSeatNum) {
        this.sbbSeatNum = sbbSeatNum;
    }

    public String getSbbBusType() {
        return sbbBusType;
    }

    public void setSbbBusType(String sbbBusType) {
        this.sbbBusType = sbbBusType;
    }

    public String getSbbPlateNumber() {
        return sbbPlateNumber;
    }

    public void setSbbPlateNumber(String sbbPlateNumber) {
        this.sbbPlateNumber = sbbPlateNumber;
    }

    public String getSbbDriverName() {
        return sbbDriverName;
    }

    public void setSbbDriverName(String sbbDriverName) {
        this.sbbDriverName = sbbDriverName;
    }

    public String getSbbDriverTel() {
        return sbbDriverTel;
    }

    public void setSbbDriverTel(String sbbDriverTel) {
        this.sbbDriverTel = sbbDriverTel;
    }
}
