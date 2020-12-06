package com.ky.gps.dao;

import com.ky.gps.entity.SbBusPosition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 校车位置表的Dao
 */
public interface SbBusPositionDao {

    /**
     * 根据路线id查询路线最新定位
     *
     * @param routeId 路线id
     * @return 返回最新定位信息
     */
    Map<String, Object> findByRouteId(@Param("routeId") Integer routeId);

    /**
     * 插入校车位置记录
     *
     * @param sbBusPosition 校车位置对象
     */
    void savePosition(SbBusPosition sbBusPosition);

    /**
     * 根据GPSId查询该校车所有位置信息
     *
     * @param gpsId GPSid
     * @return 返回存放位置基本信息的list
     */
    List<Map<String, Object>> findAllPositionByBusId(@Param("gpsId") String gpsId);

    /**
     * 查找本星期的所有路线的校车定位信息
     *
     * @param week 本星期的星期几，星期一、星期二...
     * @return 返回所有路线的校车定位信息, Keys={routeName, startTime, endTime, recodeTime, longitude, latitude, velocity, direction}
     */
    List<Map<String, Object>> findAllRoutePosition(@Param("week") String week);

    /**
     * 根据路线id查询对应星期的该路线的校车定位信息
     *
     * @param week       本星期的星期几，星期一、星期二...
     * @param routeId    待查询的路线id
     * @param startIndex 开始查询的索引位置(不包含索引位置)
     * @param count      开始查询的位置
     * @return 返回所有路线的校车定位信息, Keys={startTime, endTime, recodeTime, longitude, latitude, velocity, direction}
     */
    List<Map<String, Object>> findAllRoutePositionByRouteId(@Param("week") String week, @Param("routeId") Integer routeId, @Param("startIndex") Integer startIndex, @Param("count") Integer count);

    /**
     * 查询路线id对应的本星期的该路线的校车定位记录数量
     *
     * @param week    本星期的星期几，星期一、星期二...
     * @param routeId 待查询的路线id
     * @return 返回记录总数
     */
    Integer findCountByRouteId(@Param("week") String week, @Param("routeId") Integer routeId);

    /**
     * 删除表中的所有记录
     */
    void deleteAll();

    /**
     * 根据路线id查询该路线的bus最新的定位数据
     *
     * @param routeId 路线id
     * @param week    当天的星期
     * @return 将记录存入map返回
     */
    Map<String, Object> findNewPositionByRouteId(@Param("week") String week, @Param("routeId") Integer routeId);

}
