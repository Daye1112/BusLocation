package com.ky.gps.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author Daye
 * GPS设备的Dao
 */
public interface SbGpsDao {

    /**
     * 根据校车id来查询GPS的id
     *
     * @param busId 校车id
     * @return GPS的id
     */
    String findIdByBusId(@Param("busId") Integer busId);
}
