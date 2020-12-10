package com.ky.gps.util;

import java.util.Random;

/**
 * 随机数处理工具类
 *
 * @author Daye
 */
public class RandomUtil {

    private final static String RANDOM_STR_LETTER_NUMBER = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
    private final static String RANDOM_STR_NUMBER = "0123456789";

    /**
     * 随机产生一个长度为n的由数字组成的字符串
     *
     * @return 返回生成的字符串
     */
    public static String randomNumberChar(int len) {
        Random r = new Random();
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < len; i++) {
            res.append(RANDOM_STR_NUMBER.charAt(r.nextInt(RANDOM_STR_NUMBER.length())));
        }
        return res.toString();
    }

    /**
     * 随机产生一个长度为n的字符串
     *
     * @return 返回生成的字符串
     */
    public static String randomChar(int len) {
        Random r = new Random();
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < len; i++) {
            res.append(RANDOM_STR_LETTER_NUMBER.charAt(r.nextInt(RANDOM_STR_LETTER_NUMBER.length())));
        }
        return res.toString();
    }

}
