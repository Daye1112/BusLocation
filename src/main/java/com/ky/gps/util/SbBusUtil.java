package com.ky.gps.util;

import com.ky.gps.entity.SbBus;

/**
 * 校车工具类
 *
 * @author Darren
 */
public class SbBusUtil {

    public static boolean verifyBusExcludeId(SbBus sbBus) {
        return sbBus != null
                && IntegerUtil.isValid(sbBus.getSbbSeatNum())
                && StringUtil.isNotEmpty(sbBus.getSbbPlateNumber())
                && StringUtil.isNotEmpty(sbBus.getSbbDriverName())
                && StringUtil.isNotEmpty(sbBus.getSbbDriverTel());
    }

    public static boolean verifyBus(SbBus sbBus) {
        return sbBus != null
                && IntegerUtil.isValid(sbBus.getId())
                && IntegerUtil.isValid(sbBus.getSbbSeatNum())
                && StringUtil.isNotEmpty(sbBus.getSbbPlateNumber())
                && StringUtil.isNotEmpty(sbBus.getSbbDriverName())
                && StringUtil.isNotEmpty(sbBus.getSbbDriverTel());
    }
}
