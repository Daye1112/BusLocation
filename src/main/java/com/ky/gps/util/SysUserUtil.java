package com.ky.gps.util;

import com.ky.gps.entity.SysUser;
import com.ky.gps.service.DepartmentService;

/**
 * 用户相关工具类
 *
 * @author Daye
 */
public class SysUserUtil {

    /**
     * 判断部门和角色id是否存在
     *
     * @param sysUser 传入的用户信息
     * @return 有效:true; 无效:false
     */
    public static Boolean isEffectiveDepartmentAndRoleId(DepartmentService departmentService, SysUser sysUser) {
        if (null == sysUser.getDepartment()
                || 0 >= sysUser.getDepartment().getId() || null == sysUser.getDepartment().getId()) {
            return false;
        }
        //判断部门和角色是否存在
        return (null != departmentService.findNameById(String.valueOf(sysUser.getDepartment().getId())).getData());
    }

    /**
     * 判断待save的数据是否存在空值
     *
     * @param sysUser 待保存的用户信息
     * @return 空:true;不为空:false
     */
    public static Boolean isSavedAllInfoNull(SysUser sysUser) {
        return (null == sysUser.getDepartment().getId() || 0 >= sysUser.getDepartment().getId()
                || "".equals(sysUser.getWorkId()) || null == sysUser.getWorkId()
                || "".equals(sysUser.getRealName()) || null == sysUser.getRealName()
                || "".equals(sysUser.getPassword()) || null == sysUser.getPassword()
                || "".equals(sysUser.getSalt()) || null == sysUser.getSalt()
                || "".equals(sysUser.getIdCard()) || null == sysUser.getIdCard()
                || "".equals(sysUser.getPhone()) || null == sysUser.getPhone()
                || "".equals(sysUser.getEmail()) || null == sysUser.getEmail()
                || null == sysUser.getAccountDate()
                || "".equals(sysUser.getCreatedBy()) || null == sysUser.getCreatedBy()
                || "".equals(sysUser.getUpdatedBy()) || null == sysUser.getUpdatedBy());
    }
}
