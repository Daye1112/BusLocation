package com.ky.gps.entity;

import java.sql.Timestamp;

/**
 * @author Daye
 */
public class Department extends AbstractEntity {

    private Integer id;
    private String departmentName;
    private String code;
    private String upLevel;
    private Integer parentId;
    private Byte leaf;

    /**
     * 无参构造方法
     */
    public Department() {
    }

    /**
     * 有参构造方法-自身属性
     */
    public Department(Integer id, String departmentName,
                      String code, String upLevel,
                      Integer parentId, Byte leaf) {
        this.id = id;
        this.departmentName = departmentName;
        this.code = code;
        this.upLevel = upLevel;
        this.parentId = parentId;
        this.leaf = leaf;
    }

    /**
     * 有参构造方法-所有属性
     */
    public Department(String remark, String remark1,
                      String remark2, String remark3,
                      Timestamp createdDate, String createdBy,
                      Timestamp updatedDate, String updatedBy,
                      Boolean valid, Integer id,
                      String departmentName, String code,
                      String upLevel, Integer parentId,
                      Byte leaf) {
        super(remark, remark1,
                remark2, remark3,
                createdDate, createdBy,
                updatedDate, updatedBy,
                valid);
        this.id = id;
        this.departmentName = departmentName;
        this.code = code;
        this.upLevel = upLevel;
        this.parentId = parentId;
        this.leaf = leaf;
    }

    /**
     * 重写toString方法
     *
     * @return 返回属性值
     */
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", code='" + code + '\'' +
                ", upLevel='" + upLevel + '\'' +
                ", parentId=" + parentId +
                ", leaf=" + leaf +
                '}' +
                super.toString();
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String ndepartmentNameame) {
        this.departmentName = departmentName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpLevel() {
        return upLevel;
    }

    public void setUpLevel(String upLevel) {
        this.upLevel = upLevel;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Byte getLeaf() {
        return leaf;
    }

    public void setLeaf(Byte leaf) {
        this.leaf = leaf;
    }
}
