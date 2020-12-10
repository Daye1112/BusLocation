package com.ky.gps.util;

import com.ky.gps.entity.ResultWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * map工具类
 */
public class MapUtil {

    /**
     * 从map中提取userId到外层
     *
     * @param userList 用户信息list
     * @return 返回提取后的list
     */
    public static List<Map<String, Object>> extractUserIdFromMap(List<Map<String, Object>> userList) {
        //判断是否为空
        if (userList == null) {
            return null;
        }
        //创建待返回的list
        List<Map<String, Object>> newUserList = new ArrayList<>();
        //遍历老的list
        for (Map<String, Object> map : userList) {
            //创建临时map
            Map<String, Object> tmp = new HashMap<>(16);
            //提取userId
            tmp.put("id", map.get("id"));
            //移除旧map中的userId
            map.remove("id");
            //将map加入到新的map中
            tmp.put("content", map);
            //将tmp加入到list中
            newUserList.add(tmp);
        }
        return newUserList;
    }

    /**
     * 将页的总记录数和总页数封装进map中，再讲map封装进json对象中返回
     *
     * @param totalRecoding 总记录数
     * @param totalPages    总页数
     * @return 返回json对象
     */
    public static ResultWrapper setTotalInfoIntoMap(int totalRecoding, int totalPages) {
        //创建map
        Map<String, Object> map = new HashMap<>();
        //将总记录数和总页数存入map中
        map.put("totalRecoding", totalRecoding);
        map.put("totalPages", totalPages);
        //封装进json对象中返回
        return ResultWrapperUtil.setSuccessOf(map);

    }
}
