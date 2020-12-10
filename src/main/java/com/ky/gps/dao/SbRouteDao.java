package com.ky.gps.dao;

import com.ky.gps.entity.SbRoute;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 校车路线Dao
 */
public interface SbRouteDao {

    /**
     * 根据gpsId查询
     *
     * @param gpsId gpsId
     * @return 返回路线信息
     */
    List<Map<String, Object>> findByGpsId(@Param("id") String gpsId);

    /**
     * 根据路线名模糊查询路线信息
     *
     * @param sbrRouteName 路线名
     * @return 返回路线信息集合
     */
    List<Map<String, Object>> findByNameFuzzy(@Param("sbrRouteName") String sbrRouteName);

    /**
     * 插入路线信息
     *
     * @param sbRoute 待插入的路线对象
     */
    void save(SbRoute sbRoute);

    /**
     * 根据id更新valid值
     *
     * @param id    路线id
     * @param valid 标志位的值
     */
    void updateValidById(@Param("id") Integer id, @Param("valid") Integer valid);

    /**
     * 根据id查询所有基本属性
     *
     * @param id 待查询的路线id
     * @return 返回路线对象
     */
    Map<String, Object> findById(@Param("id") Integer id);

    /**
     * 根据id更新记录
     *
     * @param sbRoute 待更新的记录
     */
    void updateById(SbRoute sbRoute);

    /**
     * 根据id查询路线的基本信息
     *
     * @param id 查询id
     * @return 返回对象
     */
    SbRoute findBaseInfoById(@Param("id") Integer id);

    /**
     * 根据id查询路线名
     *
     * @param id 查询id
     * @return 路线名
     */
    String findNameById(@Param("id") Integer id);

    /**
     * 查询所有路线的基本信息
     *
     * @return 所有路线基本信息的list
     */
    List<Map<String, Object>> findAllBaseInfo();

    /**
     * 查询所有路线的id和路线名
     *
     * @return 返回routeList
     */
    List<Map<String, Object>> findAllIdAndName();
}
