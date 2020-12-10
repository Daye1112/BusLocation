package com.ky.gps.dao;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * <p>
 * 路线行驶轨迹表实体类Dao持久层
 */
public interface SbRoutePositionDao {
    /**
     * 根据路线id查询该路线的行驶轨迹
     *
     * @param routeId 路线id
     * @return 返回list集合
     */
    List<Map<String, Object>> findLonAndLatByRouteId(Integer routeId);
}