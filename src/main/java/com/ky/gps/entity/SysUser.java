package com.ky.gps.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Daye
 * 系统用户
 */
public class SysUser extends AbstractEntity {

    /**
     * ID
     */
    private Integer id;
    /**
     * 部门对象
     */
    private Department department;
    /**
     * 职工编号
     */
    private String workId;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密密码
     */
    private String salt;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 账户创建日期
     */
    private Date accountDate;
    /**
     * 上一次密码修改日期
     */
    private Date lastPsdDate;


    /**
     * 无参构造方法
     */
    public SysUser() {
    }

    /**
     * 有参构造方法-自身属性
     */
    public SysUser(Integer id, Department department,
                   String workId, String realName,
                   String password, String salt,
                   String idCard, String phone,
                   String email, Date accountDate,
                   Date lastPsdDate) {
        this.id = id;
        this.department = department;
        this.workId = workId;
        this.realName = realName;
        this.password = password;
        this.salt = salt;
        this.idCard = idCard;
        this.phone = phone;
        this.email = email;
        this.accountDate = accountDate;
        this.lastPsdDate = lastPsdDate;
    }

    /**
     * 有参构造方法-所有属性
     */
    public SysUser(String remark, String remark1,
                   String remark2, String remark3,
                   Timestamp createdDate, String createdBy,
                   Timestamp updatedDate, String updatedBy,
                   Boolean valid, Integer id,
                   Department department, String workId,
                   String realName, String password,
                   String salt, String idCard,
                   String phone, String email,
                   Date accountDate, Date lastPsdDate) {
        super(remark, remark1,
                remark2, remark3,
                createdDate, createdBy,
                updatedDate, updatedBy,
                valid);
        this.id = id;
        this.department = department;
        this.workId = workId;
        this.realName = realName;
        this.password = password;
        this.salt = salt;
        this.idCard = idCard;
        this.phone = phone;
        this.email = email;
        this.accountDate = accountDate;
        this.lastPsdDate = lastPsdDate;
    }

    /**
     * 重写toString
     *
     * @return 返回实体属性
     */
    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", department=" + department +
                ", workId='" + workId + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", accountDate=" + accountDate +
                ", lastPsdDate=" + lastPsdDate +
                '}' +
                super.toString();
    }

    /**
     * getter/setter start
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public Date getLastPsdDate() {
        return lastPsdDate;
    }

    public void setLastPsdDate(Date lastPsdDate) {
        this.lastPsdDate = lastPsdDate;
    }
}
