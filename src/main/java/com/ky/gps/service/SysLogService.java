package com.ky.gps.service;

import com.ky.gps.entity.SysLog;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 用户操作记录Service接口
 */
public interface SysLogService {

    /**
     * 根据用户number、name、operator模糊查询记录
     * 根据日期精确查询指定日期记录
     *
     * @param userNumber  用户编号
     * @param name        用户姓名
     * @param operator    用户操作
     * @param createdDate 创建日期
     * @return 返回日志记录
     */
    List<Map<String, Object>> findByUserAndNameAndOperatorAndCreatedDate(String userNumber, String name, String operator, String createdDate);

    /**
     * 插入用户操作记录
     *
     * @param sysLog 用户信息和操作记录
     */
    void saveSysLog(SysLog sysLog);
}
