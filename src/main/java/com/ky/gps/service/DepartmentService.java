package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;

/**
 * @author Daye
 * <p>
 * 部门服务层Service接口
 */
public interface DepartmentService {

    /**
     * 查询所有部门的id和部门名
     *
     * @return 返回list， map的key为{}
     */
    ResultWrapper findAllNameAndId();

    /**
     * 根据id查询部门name
     *
     * @param id 部门id
     * @return 封装name值到json对象
     */
    ResultWrapper findNameById(String id);
}
