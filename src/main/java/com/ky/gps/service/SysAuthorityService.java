package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysAuthority;

import java.util.List;

/**
 * 权限service层-接口类
 *
 * @author Daye
 */
public interface SysAuthorityService {

    /**
     * 查询所有权限记录
     *
     * @return 返回json对象
     */
    ResultWrapper findAll();

    /**
     * 查询所有权限码
     *
     * @return 返回字符list
     */
    List<String> findAllSaName();

    /**
     * 添加权限 - 检验空值
     *
     * @param sysAuthority 待添加的权限对象
     * @return 返回json对象
     */
    ResultWrapper insertSelective(SysAuthority sysAuthority);

    /**
     * 更新权限 - 检验空值
     *
     * @param sysAuthority 待修改的权限对象
     */
    ResultWrapper updateByPrimaryKeySelective(SysAuthority sysAuthority);
}
