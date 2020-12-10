package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;

/**
 * @author Daye
 * <p>
 * 路线行驶轨迹Service服务层接口类
 */
public interface SbRoutePositionService {
    /**
     * 根据路线id查询该路线的行驶轨迹
     *
     * @param routeId 路线id
     * @return json格式数据
     */
    ResultWrapper findLonAndLatByRouteId(String routeId);
}
