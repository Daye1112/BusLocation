package com.ky.gps.dao;

import com.ky.gps.entity.SbUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * <p>
 * 用户和角色多对多对应表的Dao
 */
public interface SbUserRoleDao {

    /**
     * 根据workId, realName, departmentId来查询用户角色list
     *
     * @param params 参数map，包含
     *               workId 用户工号
     *               realName 用户name
     *               departmentId   部门id
     * @return 返回json对象
     */
    List<Map<String, Object>> fuzzyQueryByWorkIdAndRealNameAndDepartmentId(Map<String, Object> params);

    /**
     * 批量更新指定用户的角色的标志位
     *
     * @param userId     用户id
     * @param roleIdList 待更新的角色记录集合
     * @param value      更新的值
     */
    void batchUpdateValidByUserId(@Param("userId") Integer userId, @Param("roleIdList") List<Integer> roleIdList, @Param("value") Integer value);

    /**
     * 批量添加指定用户的角色信息
     *
     * @param userId     用户id
     * @param roleIdList 角色idList
     */
    void batchSaveByUserId(@Param("userId") Integer userId, @Param("roleIdList") List<Integer> roleIdList);

    /**
     * 根据用户id查询其拥有的所有角色id
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    List<Integer> findRoleIdByUserId(@Param("userId") Integer userId);

    /**
     * 查询所有用户和其角色信息
     *
     * @return 返回map集合
     */
    List<Map<String, Object>> findAllUserAndRole();

    /**
     * 根据角色id将记录置为无效
     *
     * @param roleId 角色id
     * @param value  更新的值
     */
    void updateValidByRoleId(@Param("roleId") Integer roleId, @Param("value") Integer value);

    /**
     * 根据用户id查询该用户的所有角色
     *
     * @param id 用户id
     * @return 返回角色名list
     */
    List<String> findSysRoleSrSourceBySysUserId(@Param("id") Integer id);

    /**
     * 根据userId和roleId将用户和角色关联起来
     *
     * @param sbUserRole 待保存的对象
     */
    void saveUserRole(SbUserRole sbUserRole);

    /**
     * 根据userId删除用户与角色的对应记录
     * 并不是真正的删除，将Valid设为0，即无效
     *
     * @param userId   用户id
     * @param updateBy 更新者workId
     */
    void deleteUserRoleByUserId(@Param("userId") Integer userId, @Param("updateBy") String updateBy);

}
