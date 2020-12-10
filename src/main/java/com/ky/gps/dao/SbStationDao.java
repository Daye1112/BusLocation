package com.ky.gps.dao;

import com.ky.gps.entity.SbStation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 站点信息Dao
 */
public interface SbStationDao {

    /**
     * 查询所有站点名
     *
     * @return 返回站点名集合
     */
    List<String> findNames();

    /**
     * 根据id查询站点信息
     *
     * @param id 查询的站点id
     * @return 返回map
     */
    Map<String, Object> findById(@Param("id") Integer id);

    /**
     * 根据id更新站点信息
     *
     * @param sbStation 站点对象
     */
    void updateInfoById(SbStation sbStation);

    /**
     * 根据id更新记录的valid值
     *
     * @param id    站点id
     * @param valid 更新的值
     */
    void updateValidById(@Param("id") Integer id, @Param("valid") Integer valid);

    /**
     * 插入站点记录
     *
     * @param sbStation 站点对象
     */
    void insert(SbStation sbStation);

    /**
     * 查询所有站点信息
     *
     * @return 返回站点信息list
     */
    List<Map<String, Object>> findAll();

    /**
     * 根据站点名模糊查询所有站点list
     *
     * @param sbsStation 站点名
     * @return 返回站点集合
     */
    List<Map<String, Object>> findFuzzyBySvsStation(@Param("sbsStation") String sbsStation);
}
