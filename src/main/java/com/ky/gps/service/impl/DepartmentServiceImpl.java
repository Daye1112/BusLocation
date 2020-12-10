package com.ky.gps.service.impl;

import com.ky.gps.dao.DepartmentDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.service.DepartmentService;
import com.ky.gps.util.ResultWrapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 部门服务层Service接口实现类
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentDao departmentDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findAllNameAndId() {
        List<Map<String, Object>> deptList = departmentDao.findAllNameAndId();
        return ResultWrapperUtil.setSuccessOf(deptList);
    }

    //    @Cacheable(value = "department", key = "#id")
    @Override
    public ResultWrapper findNameById(String id) {
        //查询id对应的name
        String name = departmentDao.findNameById(Integer.parseInt(id));
        //将name封装到json对象中返回
        return ResultWrapperUtil.setSuccessOf(name);
    }
}
