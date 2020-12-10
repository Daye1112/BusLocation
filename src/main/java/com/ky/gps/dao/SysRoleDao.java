package com.ky.gps.dao;

import com.ky.gps.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * <p>
 * 角色表Dao数据访问层
 */
public interface SysRoleDao {

    /**
     * 查询所有角色的id和name
     *
     * @return 返回map集合
     */
    List<Map<String, Object>> findIdAndSrName();

    /**
     * 根据创建日期查询角色信息
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 返回查询role结果list
     */
    List<Map<String, Object>> findByCreatedDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 根据id更新角色记录
     *
     * @param sysRole 待更新的角色对象
     */
    void updateById(SysRole sysRole);

    /**
     * 根据id查询角色信息
     *
     * @param id 待查询的id
     * @return 返回map键值对
     */
    Map<String, Object> findById(@Param("id") Integer id);

    /**
     * 插入角色记录
     *
     * @param sysRole 待插入的角色
     */
    void saveRole(SysRole sysRole);

    /**
     * 根据id将角色置为无效
     *
     * @param roleId 待更新的id
     * @param value  待更新的值
     */
    void updateValidById(@Param("roleId") Integer roleId, @Param("value") Integer value);

    /**
     * 查询所有角色信息
     *
     * @return 返回角色信息list
     */
    List<Map<String, Object>> findAllRole();

    /**
     * 根据id查询角色名
     *
     * @param roleId 待查询的角色id
     * @return name值
     */
    String findNameById(@Param("roleId") Integer roleId);
}
