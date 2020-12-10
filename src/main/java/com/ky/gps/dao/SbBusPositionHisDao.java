package com.ky.gps.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 */
public interface SbBusPositionHisDao {

    /**
     * 根据日期和路线id查询定位
     *
     * @param routeId    路线id
     * @param recodeTime 创建日期
     * @return 返回定位信息集合
     */
    List<Map<String, Object>> findByRouteIdAndCreatedDate(@Param("routeId") Integer routeId, @Param("recodeTime") String recodeTime);

    /**
     * 将当前位置表中的所有记录插入到历史表中
     */
    void insertFromSbBusPosition();

}
