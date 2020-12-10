package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SbRoleAuthority;

import java.util.List;

/**
 * 角色权限service层-接口类
 *
 * @author Daye
 */
public interface SbRoleAuthorityService {

    /**
     * 根据角色id更新角色权限
     *
     * @param roleId           角色id
     * @param idList           待添加的权限id
     * @param needDeleteIdList 待删除的权限id
     * @return 返回json对象
     */
    ResultWrapper updateByRoleId(Integer roleId, List<Integer> idList, List<Integer> needDeleteIdList);

    /**
     * 根据角色id查询该角色的所有权限
     *
     * @param roleId 角色id
     * @return 返回json对象
     */
    ResultWrapper findAllAuthorityByRoleId(Integer roleId);

    /**
     * 根据roleId查询所有权限id
     *
     * @param roleId 角色id
     * @return 返回json对象
     */
    ResultWrapper findAuthorityIdByRoleId(Integer roleId);

    /**
     * 批量插入角色id和对应的权限id
     *
     * @param roleId          角色id
     * @param authorityIdList 权限idList
     * @return 返回json对象
     */
    ResultWrapper batchSaveRoleIdAndAuthorityId(Integer roleId, List<Integer> authorityIdList);

    /**
     * 根据角色代码名查询角色所拥有的所有权限
     *
     * @param roles 角色代码名list
     * @return 返回权限代码list
     */
    List<String> findSaNameBySrSource(List<String> roles);

    /**
     * 插入角色权限记录
     *
     * @param sbRoleAuthority 待插入的角色权限对象
     * @return 返回json对象
     */
    ResultWrapper insertSelective(SbRoleAuthority sbRoleAuthority);

    /**
     * 更新角色权限记录
     *
     * @param sbRoleAuthority 待更新的权限对象
     */
    ResultWrapper updateByPrimaryKeySelective(SbRoleAuthority sbRoleAuthority);
}
