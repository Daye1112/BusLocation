package com.ky.gps.entity;

/**
 * 用户将权限转换为Json对象
 *
 * @author Daye
 */
public class SysAuthorityExtractAttribute {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限名
     */
    private String saDisplayName;
    /**
     * 是否选中
     */
    private Boolean checked;

    public SysAuthorityExtractAttribute() {
    }

    public SysAuthorityExtractAttribute(Integer id, String saDisplayName, Boolean checked) {
        this.id = id;
        this.saDisplayName = saDisplayName;
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "SysAuthorityExtractAttribute{" +
                "id=" + id +
                ", saDisplayName='" + saDisplayName + '\'' +
                ", checked=" + checked +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaDisplayName() {
        return saDisplayName;
    }

    public void setSaDisplayName(String saDisplayName) {
        this.saDisplayName = saDisplayName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}