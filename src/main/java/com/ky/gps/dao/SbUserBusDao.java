package com.ky.gps.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 用户和路线多对多的Dao数据访问层
 */
public interface SbUserBusDao {

    /**
     * 根据用户id查询该用户能查看的所有路线id和name
     *
     * @param userId 用户id
     * @return 返回map
     */
    List<Map<String, Object>> findRouteIdAndRouteNameByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户id删除该用户和路线对应的记录
     * 不是真删除，而是将Valid设为0，即无效
     *
     * @param userId   用户id
     * @param updateBy 更新者的workId
     */
    void deleteUserBusByUserId(@Param("userId") Integer userId, @Param("updateBy") String updateBy);

    /**
     * 根据筛选条件查询用户路线信息
     *
     * @param params 查询条件
     * @return 返回map
     */
    List<Map<String, Object>> list(Map<String, Object> params);

    /**
     * 根据用户id删除所有分配路线记录
     *
     * @param userId 用户id
     */
    void deleteByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户id的分配路线记录
     *
     * @param userId   用户id
     * @param routeIds 路线id集合
     */
    void insert(@Param("userId") Integer userId, @Param("routeIds") List<Integer> routeIds);
}
