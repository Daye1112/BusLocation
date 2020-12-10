package com.ky.gps.service;

import com.ky.gps.entity.SbBusRoute;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 */
public interface SbBusRouteService {

    /**
     * 更新绑定信息
     *
     * @param sbBusRoute 待更新的对象
     * @return 返回更新后的绑定信息
     */
    Map<String, Object> update(SbBusRoute sbBusRoute);

    /**
     * 插入记录
     *
     * @param sbBusRoute 待插入的对象
     * @return 返回绑定信息
     */
    Map<String, Object> save(SbBusRoute sbBusRoute);

    /**
     * 根据id更新有效位
     *
     * @param id id
     */
    void updateValidById(Integer id);

    /**
     * 根据路线id和星期查询路线的校车绑定信息
     * 通过时间筛选需要的数据
     *
     * @param routeId   路线id
     * @param week      星期
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回信息集合
     */
    List<Map<String, Object>> findByRouteId(Integer routeId, String week, String startTime, String endTime);

    /**
     * 查询所有的联系
     * 不包含备注
     *
     * @return 对象list
     */
    List<SbBusRoute> findAllRelation();
}
