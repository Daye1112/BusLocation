package com.ky.gps.controller.manage;

import com.ky.gps.annotation.PermissionName;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.service.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Daye
 * 部门处理器
 */
@Controller
@RequestMapping(value = "/m/department")
public class DepartmentManageHandler {
    /**
     * 日志打印对象
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentManageHandler.class);

    @Resource
    private DepartmentService departmentService;

    /**
     * 部门id,name查询
     *
     * @return 返回json格式数据
     */
    @RequiresPermissions("dept:query")
    @PermissionName(group = "部门管理", displayName = "部门查询")
    @RequestMapping(value = "/base/info", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper findAllIdAndName() {
        ResultWrapper resultWrapper;
        resultWrapper = departmentService.findAllNameAndId();
        return resultWrapper;
    }
}
