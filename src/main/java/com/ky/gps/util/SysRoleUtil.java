package com.ky.gps.util;

import com.ky.gps.entity.SysRole;

/**
 * 角色操作工具类
 *
 * @author Daye
 */
public class SysRoleUtil {

    /**
     * 在插入记录前判断待插入记录是否合法
     *
     * @param sysRole 待校验的角色对象
     * @return true or false
     */
    public static boolean checkEffectiveBeforeInsert(SysRole sysRole) {
        if (StringUtil.isEmpty(sysRole.getRemark())) {
            sysRole.setRemark("");
        }
        return StringUtil.isNotEmpty(sysRole.getSrName()) && StringUtil.isNotEmpty(sysRole.getSrSource())
                && sysRole.getSrManage() != null && IntegerUtil.isValid(sysRole.getSrLevel());
    }
}
