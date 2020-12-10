package com.ky.gps.dao;

import com.ky.gps.entity.SysAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 权限dao层
 *
 * @author Daye
 */
public interface SysAuthorityDao {

    /**
     * 根据id查询权限信息
     *
     * @param id 权限id
     * @return 返回权限map
     */
    Map<String, Object> findById(@Param("id") Integer id);

    /**
     * 查询所有权限记录
     *
     * @return 返回权限list
     */
    List<Map<String, Object>> findAll();

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
     */
    void insertSelective(SysAuthority sysAuthority);

    /**
     * 更新权限 - 检验空值
     *
     * @param sysAuthority 待修改的权限对象
     */
    void updateByPrimaryKeySelective(SysAuthority sysAuthority);

}