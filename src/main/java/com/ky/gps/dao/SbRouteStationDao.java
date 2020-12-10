package com.ky.gps.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 路线站点对应Dao
 */
public interface SbRouteStationDao {

    /**
     * 根据路线id查询所有站点信息
     *
     * @param routeId 路线id
     * @return 返回站点信息list
     */
    List<Map<String, Object>> findByRouteId(@Param("routeId") Integer routeId);

    /**
     * 修改路线站点绑定信息
     *
     * @param routeId       路线id
     * @param stationId     站点id
     * @param sbsDepartTime 发车时间
     */
    void updateDepartTimeByRouteIdAndStationId(@Param("stationId") Integer stationId, @Param("routeId") Integer routeId, @Param("sbsDepartTime") String sbsDepartTime);

    /**
     * 根据路线id和站点id更新valid值
     *
     * @param stationId 站点id
     * @param routeId   路线id
     * @param valid     标致位的值
     */
    void updateValidByRouteIdAndStationId(@Param("stationId") Integer stationId, @Param("routeId") Integer routeId, @Param("valid") Integer valid);

    /**
     * 根据id查询记录
     *
     * @param id 路线站点id
     * @return 返回路线站点信息
     */
    Map<String, Object> findById(@Param("id") Integer id);

    /**
     * 添加路线站点绑定信息
     *
     * @param routeId       路线id
     * @param stationId     站点id
     * @param sbsDepartTime 发车时间
     */
    void save(@Param("stationId") Integer stationId, @Param("routeId") Integer routeId, @Param("sbsDepartTime") String sbsDepartTime);

    /**
     * 根据站点id和路线id查询记录
     *
     * @param routeId   路线id
     * @param stationId 站点id
     * @return 返回路线站点信息
     */
    Map<String, Object> findByRouteIdAndStationId(@Param("routeId") Integer routeId, @Param("stationId") Integer stationId);

    /**
     * 根据站点id更新valid标志位
     *
     * @param stationId 站点id
     * @param valid     标志位的值
     */
    void updateValidByStationId(@Param("stationId") Integer stationId, @Param("valid") Integer valid);

    /**
     * 查找路线id对应的所有有效站点
     *
     * @param routeId 路线id
     * @return 所有有效站点List集合
     */
    List<Map<String, Object>> findStationByRouteId(@Param("routeId") Integer routeId);

    /**
     * 查询所有路线对应的站点信息
     * 但未根据发车时间排序，因此程序需要对发车时间进行排序
     *
     * @return 所有路线对应站点的信息，keys={routeName, stationName, longitude, latitude, departTime}
     */
    List<Map<String, Object>> findAllRouteStation();

    /**
     * 实时查询路线站点信息
     * 将过滤不在运营时间内的路线
     *
     * @param week 当天的星期
     * @return 返回list, keys={routeId, routeName, stationName, longitude, latitude, departTime, startTime, endTime}
     */
    List<Map<String, Object>> findRealTimeAllRouteStation(@Param("week") String week);

}
