package com.ky.gps.util;

/**
 * @author Daye
 * 整型数据工具类
 */
public class IntegerUtil {

    /**
     * 判断数字是否有效 即>0 and != null
     *
     * @param num 待判断的数字
     * @return true-有效；false-无效
     */
    public static boolean isValid(Integer num) {
        return (num != null && num > 0);
    }

    /**
     * 判断数字是否有效 即<=0 or == null
     *
     * @param num 待判断的数字
     * @return true-无效；false-有效
     */
    public static boolean isNotValid(Integer num) {
        return (num == null || num <= 0);
    }
}
