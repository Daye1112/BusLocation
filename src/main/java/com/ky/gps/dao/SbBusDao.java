package com.ky.gps.dao;

import com.ky.gps.entity.SbBus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 校车类的Dao
 *
 * @author Daye
 */
public interface SbBusDao {

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
    List<Map<String, Object>> findByDriverNameAndBusType(@Param("sbbDriverName") String sbbDriverName, @Param("sbbBusType") String sbbBusType);

    /**
     * 插入校车记录
     *
     * @param sbBus 待插入的校车对象
     */
    void insert(SbBus sbBus);

    /**
     * 根据id更新valid值
     *
     * @param id    校车id
     * @param valid 值
     */
    void updateValidById(@Param("id") Integer id, @Param("valid") Integer valid);

    /**
     * 根据id查询校车信息
     *
     * @param id 校车id
     * @return 返回校车信息
     */
    Map<String, Object> findById(@Param("id") Integer id);

    /**
     * 根据id更新校车信息
     *
     * @param sbBus 待更新的校车对象
     */
    void update(SbBus sbBus);

    /**
     * 查询所有校车记录
     *
     * @return 返回校车信息集合
     */
    List<Map<String, Object>> find();

    /**
     * 根据id查询校车的车牌号
     *
     * @param id 待查询的校车id
     * @return 车牌号
     */
    String findPlateNumberById(@Param("id") Integer id);
}
