package com.ky.gps.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色权限工具类
 *
 * @author Daye
 */
public class SbRoleAuthorityUtil {

    /**
     * 从提交的参数中提取checked=false且目前有的权限id
     *
     * @param authorityMap 提交的参数list
     * @return 返回权限id的list
     */
    public static List<Integer> extractNeedDeleteIdFromParam(List<Map<String, Object>> authorityMap,
                                                             List<Integer> authorityIdList) {
        //null值处理
        authorityIdList = authorityIdList == null ? new ArrayList<>() : authorityIdList;
        List<Integer> needDeleteIdList = new ArrayList<>();
        for (Map<String, Object> map : authorityMap) {
            Integer id = (Integer) map.get("id");
            //提取其中checked为true的权限id
            if (!(boolean) map.get("checked") && authorityIdList.contains(id)) {
                needDeleteIdList.add(id);
            }
        }
        return needDeleteIdList;
    }

    /**
     * 从提交的参数中提取checked=true的权限id
     *
     * @param authorityMap 提交的参数list
     * @return 返回权限id的list
     */
    public static List<Integer> extractIdFromParam(List<Map<String, Object>> authorityMap,
                                                   List<Integer> authorityIdList) {
        //null值处理
        authorityIdList = authorityIdList == null ? new ArrayList<>() : authorityIdList;
        List<Integer> idList = new ArrayList<>();
        for (Map<String, Object> map : authorityMap) {
            Integer id = (Integer) map.get("id");
            //提取其中checked为true的权限id
            if ((boolean) map.get("checked") && !authorityIdList.contains(id)) {
                idList.add(id);
            }
        }
        return idList;
    }
}
