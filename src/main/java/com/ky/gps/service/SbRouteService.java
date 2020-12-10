package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SbRoute;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 路线实体类的Service接口
 */
public interface SbRouteService {

    /**
     * 根据路线名和发车区间模糊查询路线信息
     *
     * @param sbrRouteName 路线名
     * @param startTime    起始时间
     * @param endTime      结束时间
     * @return 返回路线信息集合
     */
    List<Map<String, Object>> findByNameAndTimeFuzzy(String sbrRouteName, String startTime, String endTime);

    /**
     * 插入路线信息
     *
     * @param sbRoute 待插入的路线对象
     * @return 返回添加后的路线信息
     */
    Map<String, Object> save(SbRoute sbRoute);

    /**
     * 根据id删除路线
     *
     * @param id 路线id
     */
    void deleteById(Integer id);

    /**
     * 根据id更新记录
     *
     * @param sbRoute 待更新的记录
     * @return 返回更新后的对象信息
     */
    Map<String, Object> updateById(SbRoute sbRoute);

    /**
     * 根据id查询路线的基本信息
     *
     * @param id 查询id
     * @return 返回对象
     */
    SbRoute findBaseInfoById(Integer id);

    /**
     * 根据id查询路线名
     *
     * @param id 查询id
     * @return 路线名
     */
    String findNameById(Integer id);

    /**
     * 查询所有路线的基本信息
     *
     * @return 所有路线基本信息的list
     */
    List<Map<String, Object>> findAllBaseInfo();

    /**
     * 查询所有路线的id和路线名
     *
     * @return 返回resultWrapper
     */
    ResultWrapper findAllIdAndName();
}
