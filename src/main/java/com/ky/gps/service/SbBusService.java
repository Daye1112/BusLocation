package com.ky.gps.service;

import com.ky.gps.entity.SbBus;

import java.util.List;
import java.util.Map;

/**
 * 校车service-接口类
 *
 * @author Darren
 */
public interface SbBusService {

    /**
     * 查询所有校车类型
     *
     * @return 返回校车类型list
     */
    List<String> findType();

    /**
     * 根据司机名和车辆类型模糊查询
     *
     * @param sbbDriverName 司机名
     * @param sbbBusType    车辆类型
     * @return 返回校车信息集合
     */
    List<Map<String, Object>> findByDriverNameAndBusType(String sbbDriverName, String sbbBusType);

    /**
     * 插入校车记录
     *
     * @param sbBus 待插入的校车对象
     * @return 返回添加的校车基本信息
     */
    Map<String, Object> insert(SbBus sbBus);

    /**
     * 根据id更新valid值
     *
     * @param id 校车id
     */
    void delete(Integer id);

    /**
     * 根据id更新校车信息
     *
     * @param sbBus 待更新的校车对象
     * @return 返回修改完后的校车信息
     */
    Map<String, Object> update(SbBus sbBus);

    /**
     * 查询所有校车记录
     *
     * @return 返回校车信息集合
     */
    List<Map<String, Object>> find();
}
