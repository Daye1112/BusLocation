package com.ky.gps.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户角色工具类
 *
 * @author Daye
 */
public class SbUserRoleUtil {

    /**
     * 提取需要添加的id集合
     *
     * @param roles      角色信息集合
     * @param roleIdList 待排除的id集合
     * @return 返回角色id集合
     */
    public static List<Integer> extractNeedAddIdFromParam(List<Integer> roles,
                                                          List<Integer> roleIdList) {
        //null值处理
        roleIdList = roleIdList == null ? new ArrayList<>() : roleIdList;
        List<Integer> needAddIdList = new ArrayList<>();
        //遍历roles
        for (Integer roleId : roles) {
            //判断id是否在已有id集合中
            if (!roleIdList.contains(roleId)) {
                needAddIdList.add(roleId);
            }
        }
        return needAddIdList;
    }

    /**
     * 提取需要删除的id集合
     *
     * @param roles      角色信息集合
     * @param roleIdList 待排除的id集合
     * @return 返回角色id集合
     */
    public static List<Integer> extractNeedDeleteIdFromParam(List<Integer> roles,
                                                             List<Integer> roleIdList) {
        //null值处理
        roleIdList = roleIdList == null ? new ArrayList<>() : roleIdList;
        List<Integer> needDeleteIdList = new ArrayList<>();
        //遍历roles
        for (Integer roleId : roleIdList) {
            if (!roles.contains(roleId)) {
                needDeleteIdList.add(roleId);
            }
        }
        return needDeleteIdList;
    }

    /**
     * 判断用户是否拥有角色，有-checked=true，无-checked=false
     *
     * @param userRoleIdList 用户拥有角色id集合
     * @param roleList       角色集合
     * @return 返回处理好后的roleList
     */
    public static List<Map<String, Object>> checkUserHasRole(List<Integer> userRoleIdList, List<Map<String, Object>> roleList) {
        //处理用户没有任何角色的情况
        if (userRoleIdList == null) {
            userRoleIdList = new ArrayList<>();
        }
        //遍历角色集合
        for (Map<String, Object> role : roleList) {
            //获取角色id
            Integer id = (Integer) role.get("id");
            //判断用户是否拥有该角色
            if (userRoleIdList.contains(id)) {
                role.put("checked", true);
            } else {
                role.put("checked", false);
            }
        }

        return roleList;
    }
}
