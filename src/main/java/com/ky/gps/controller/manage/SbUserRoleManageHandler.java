package com.ky.gps.controller.manage;

import com.ky.gps.annotation.PermissionName;
import com.ky.gps.entity.ErrorCode;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.service.SbUserRoleService;
import com.ky.gps.service.SysUserService;
import com.ky.gps.util.IntegerUtil;
import com.ky.gps.util.ResultWrapperUtil;
import com.ky.gps.util.SbUserRoleUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户角色管理
 *
 * @author Daye
 */
@Controller
@RequestMapping("/m/userRole")
public class SbUserRoleManageHandler {

    private SbUserRoleService sbUserRoleService;
    private SysUserService sysUserService;

    /**
     * 根据workId, realName, departmentId来查询用户角色list
     *
     * @param params   参数map，包含
     *                 workId 用户工号
     *                 realName 用户name
     *                 departmentId   部门id
     * @param request  请求域
     * @param response 响应域
     * @return 返回json格式数据
     */
    @PermissionName(displayName = "用户角色筛选", group = "用户角色管理")
    @RequiresPermissions("userRole:fQuery")
    @ResponseBody
    @RequestMapping(value = "/f/query", method = RequestMethod.POST)
    public ResultWrapper getByFuzzyQuery(@RequestBody(required = false) Map<String, Object> params,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        ResultWrapper resultWrapper;
        if (params == null) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            if (params.containsKey("workId")) {
                params.put("workId", "%" + params.get("workId") + "%");
            }
            if (params.containsKey("realName")) {
                params.put("realName", "%" + params.get("realName") + "%");
            }
            resultWrapper = sbUserRoleService.fuzzyQueryByWorkIdAndRealNameAndDepartmentId(params);
        }
        return resultWrapper;
    }

    /**
     * 根据用户id更新用户角色
     *
     * @param params   参数map，包含
     *                 id int 用户id
     *                 roles List<map> 用户权限状态
     * @param response 请求域
     * @param request  响应域
     * @return 返回json格式数据
     */
    @SuppressWarnings("unchecked")
    @PermissionName(displayName = "用户角色更新", group = "用户角色管理")
    @RequiresPermissions("userRole:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper updateUserRoles(@RequestBody(required = false) Map<String, Object> params,
                                         HttpServletResponse response,
                                         HttpServletRequest request) {

        ResultWrapper resultWrapper;
        //判断参数是否合法
        if (params == null || params.size() == 0) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "参数不可为空", response);
        } else {
            //获取用户id
            Integer id = (Integer) params.get("id");
            //判断id是否存在
            if (IntegerUtil.isValid(id) && sysUserService.findRealNameById(id).getData() != null) {
                //获取角色信息
                List<Integer> roles = (List<Integer>) params.get("roles");
                if (roles == null) {
                    resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "角色信息不可为空", response);
                } else {
                    Object data = sbUserRoleService.findRoleIdByUserId(id).getData();
                    //获取用户所拥有的权限list
                    List<Integer> roleIdList = data == null ? new ArrayList<>() : (List<Integer>) data;
                    List<Integer> needDeleteIdList = SbUserRoleUtil.extractNeedDeleteIdFromParam(roles, roleIdList);
                    List<Integer> needAddIdList = SbUserRoleUtil.extractNeedAddIdFromParam(roles, roleIdList);
                    sbUserRoleService.updateUserRoleByUserId(id, needDeleteIdList, needAddIdList);
                    resultWrapper = ResultWrapperUtil.setSuccessOf(null);
                }
            } else {
                resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.PARAMETER_NOT_VALID, "id不存在", response);
            }
        }
        return resultWrapper;
    }

    /**
     * 根据用户id查询用户所有角色的拥有状态
     *
     * @param params 参数map，包含
     *               id 用户id
     * @return 返回json格式
     */
    @PermissionName(displayName = "用户角色查询", group = "用户角色管理")
    @RequiresPermissions("userRole:query")
    @ResponseBody
    @RequestMapping(value = "/list/user", method = RequestMethod.POST)
    public ResultWrapper findUserRolesStatusByUserId(@RequestBody(required = false) Map<String, Object> params,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        ResultWrapper resultWrapper;
        if (params == null || params.size() == 0) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "需传入用户id", response);
        } else {
            //获取参数中的id
            Integer userId = (Integer) params.get("id");
            //判断id是否存在
            if (IntegerUtil.isValid(userId) && sysUserService.findRealNameById(userId).getData() != null) {
                //查询用户的角色拥有情况
                resultWrapper = sbUserRoleService.findRoleIdByUserId(userId);
            } else {
                resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.PARAMETER_NOT_VALID, "用户不存在", response);
            }
        }
        return resultWrapper;
    }

    /**
     * 查询所有用户的基本信息和其所拥有的所有权限
     *
     * @return 返回json格式数据
     */
    @PermissionName(displayName = "用户角色查询", group = "用户角色管理")
    @RequiresPermissions("userRole:query")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultWrapper findAllUserAndRoles() {
        ResultWrapper resultWrapper = sbUserRoleService.findAllUserAndRole();
        return resultWrapper;
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setSbUserRoleService(SbUserRoleService sbUserRoleService) {
        this.sbUserRoleService = sbUserRoleService;
    }
}
