package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SbStation;

import java.util.List;

/**
 * 站点表service层-接口
 *
 * @author Daye
 */
public interface SbStationService {

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
     * @return json对象
     */
    ResultWrapper findById(Integer id);

    /**
     * 根据id更新站点信息
     *
     * @param sbStation 站点对象
     * @return 返回json对象
     */
    ResultWrapper updateInfoById(SbStation sbStation);

    /**
     * 根据id更新记录的valid值
     *
     * @param id    站点id
     * @param valid 更新的值
     * @return json对象
     */
    ResultWrapper delete(Integer id, Integer valid);

    /**
     * 插入站点记录
     *
     * @param sbStation 站点对象
     * @return 返回json对象
     */
    ResultWrapper insert(SbStation sbStation);

    /**
     * 根据站点名模糊查询所有站点list
     *
     * @param sbsStation 站点名
     * @return 返回json对象
     */
    ResultWrapper findFuzzyQueryBySbsStation(String sbsStation);

    /**
     * 查询所有站点信息
     *
     * @return 返回json对象
     */
    ResultWrapper findAll();
}
