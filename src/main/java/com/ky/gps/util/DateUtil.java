package com.ky.gps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Darren
 */
public class DateUtil {

    /**
     * 比较两个小时字符串的大小，24小时制
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回true/false
     */
    public static boolean verifyHour(String startTime, String endTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date startDate = simpleDateFormat.parse(startTime);
        Date endDate = simpleDateFormat.parse(endTime);
        return endDate.compareTo(startDate) >= 0;
    }
}
