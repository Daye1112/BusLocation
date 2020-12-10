package com.ky.gps.service;

import java.util.List;
import java.util.Map;

/**
 * 用户路线中间表service-接口类
 *
 * @author Darren
 */
public interface SbUserBusService {
    /**
     * 根据用户id查询该用户能查看的所有路线id和name
     *
     * @param userId 用户id
     * @return 返回map
     */
    List<Map<String, Object>> findRouteIdAndRouteNameByUserId(Integer userId);

    /**
     * 根据筛选条件查询用户路线信息
     *
     * @param params 查詢條件
     * @return 返回用戶路线信息
     */
    List<Map<String, Object>> list(Map<String, Object> params);

    /**
     * 根据用户id更新用户所能查看的路线id
     *
     * @param userId   用户id
     * @param routeIds 路线id集合
     */
    void update(Integer userId, List<Integer> routeIds);
}
