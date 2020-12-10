package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 路线对应站点Service接口
 */
public interface SbRouteStationService {

    /**
     * 根据路线id查询所有站点信息
     *
     * @param routeId 路线id
     * @return 返回站点信息list
     */
    List<Map<String, Object>> findByRouteId(Integer routeId);

    /**
     * 修改路线站点绑定信息
     *
     * @param routeId       路线id
     * @param stationId     站点id
     * @param sbsDepartTime 发车时间
     */
    Map<String, Object> updateDepartTimeByRouteIdAndStationId(Integer stationId, Integer routeId, String sbsDepartTime);

    /**
     * 根据路线id和站点id更新valid值
     *
     * @param stationId 站点id
     * @param routeId   路线id
     */
    void delete(Integer stationId, Integer routeId);

    /**
     * 添加路线站点绑定信息
     *
     * @param routeId       路线id
     * @param stationId     站点id
     * @param sbsDepartTime 发车时间
     * @return 新的站点路线信息
     */
    Map<String, Object> save(Integer stationId, Integer routeId, String sbsDepartTime);

    /**
     * 根据站点id和路线id查询记录
     *
     * @param routeId   路线id
     * @param stationId 站点id
     * @return 返回路线站点信息
     */
    Map<String, Object> findByRouteIdAndStationId(Integer routeId, Integer stationId);

    /**
     * 查找路线id对应的所有有效站点
     *
     * @param routeId 路线id
     * @return 所有有效站点List集合
     */
    ResultWrapper findStationByRouteId(Integer routeId);

    /**
     * 实时查询路线站点信息
     * 将过滤不在运营时间内的路线
     *
     * @param week 星期
     * @param hour 小时
     * @return 返回Json数据
     */
    ResultWrapper findRealTimeAllRouteStation(String week, String hour);

    /**
     * 查询所有路线和其对应的站点信息
     *
     * @return 返回根据站点发车时间排序好并封装完成的Result
     */
    ResultWrapper findAllRouteStation();

}
