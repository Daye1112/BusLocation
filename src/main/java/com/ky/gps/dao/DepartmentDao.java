package com.ky.gps.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 部门类的dao类
 */
public interface DepartmentDao {

    /**
     * 查询所有部门的id和部门名
     *
     * @return 返回list， map的key为{}
     */
    List<Map<String, Object>> findAllNameAndId();

    /**
     * 根据id查询部门name
     *
     * @param id 部门id
     * @return name值
     */
    String findNameById(@Param("id") Integer id);


}
