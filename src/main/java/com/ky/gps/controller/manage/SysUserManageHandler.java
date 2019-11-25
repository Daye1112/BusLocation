package com.ky.gps.controller.manage;

import com.ky.gps.annotation.PermissionName;
import com.ky.gps.entity.ErrorCode;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysLog;
import com.ky.gps.entity.SysUser;
import com.ky.gps.service.DepartmentService;
import com.ky.gps.service.SysLogService;
import com.ky.gps.service.SysUserService;
import com.ky.gps.util.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 管理用户处理器
 *
 * @author Daye
 */
@Controller
@RequestMapping(value = "/m/user")
public class SysUserManageHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SysUserManageHandler.class);

    private SysUserService sysUserService;
    private DepartmentService departmentService;
    private SysLogService sysLogService;

    /**
     * 根据workId,realName
     *
     * @return 返回json格式
     */
    @RequiresPermissions("user:fQuery")
    @PermissionName(group = "用户管理", displayName = "用户筛选")
    @RequestMapping(value = "/f/query", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper fuzzyQueryByRealNameAndWorkIdAndDepartment(@RequestBody Map<String, Object> params) {
        if (params.containsKey("realName")) {
            params.put("realName", "%" + params.get("realName").toString() + "%");
        }
        if (params.containsKey("workId")) {
            params.put("workId", "%" + params.get("workId").toString() + "%");
        }
        return sysUserService.findBaseInfoLikeRealNameAndWorkIdAndDepartment(params);
    }

    /**
     * 根据depId查询用户，获取其总记录数和总页数
     *
     * @param depId 部门id
     * @return Json格式数据
     */
    @RequiresPermissions("user:fQuery")
    @PermissionName(group = "用户管理", displayName = "用户筛选")
    @RequestMapping(value = "/f/query/dep", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper fuzzyQueryByWorkIdTotalPages(Integer depId,
                                                      Integer pageSize,
                                                      HttpServletResponse response) {
        ResultWrapper resultWrapper;
        //1. 判断传入的参数是否有效
        if (IntegerUtil.isNotValid(depId) || IntegerUtil.isNotValid(pageSize)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            //2. 获取Service层得到的json对象
            resultWrapper = sysUserService.findTotalByDepartmentId(depId, pageSize);
        }
        return resultWrapper;
    }

    /**
     * 根据depId查询用户
     *
     * @param params 存放参数map，包含
     *               depId 部门id
     *               pageNow 当前页
     *               pageSize 页大小
     * @return Json格式数据
     */
    @RequiresPermissions("user:fQuery")
    @PermissionName(group = "用户管理", displayName = "用户筛选")
    @RequestMapping(value = "/f/query/dep", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper fuzzyQueryByDepartment(@RequestBody Map<String, Object> params,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        Integer depId = (Integer) params.get("depId");
        Integer pageNow = (Integer) params.get("pageNow");
        Integer pageSize = (Integer) params.get("pageSize");
        ResultWrapper resultWrapper;
        //1. 空值判断
        if (IntegerUtil.isNotValid(depId) || IntegerUtil.isNotValid(pageNow) || IntegerUtil.isNotValid(pageSize)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            //1.5 计算开始查询的索引
            Integer startIndex = (pageNow - 1) * pageSize;
            //2. 查询数据
            resultWrapper = sysUserService.findUserByDepartmentId(depId, startIndex, pageSize);
            //3. 日志记录
            sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "根据部门id模糊查询用户信息", "/admin/f/query/dep", "查询部门id为" + depId + ",第" + pageNow + "页, 页大小为" + pageSize + "的用户记录"));
        }// if 空值判断 end
        return resultWrapper;
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param params 存放前端请求的参数map，包含
     *               userId 用户id
     * @return json格式数据
     */
    @RequiresPermissions("user:query")
    @PermissionName(group = "用户管理", displayName = "用户查询")
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper findUserByUserId(@RequestBody Map<String, Object> params,
                                          HttpServletResponse response) {
        Integer userId = (Integer) params.get("userId");
        ResultWrapper resultWrapper;
        //判断id是否为空
        if (IntegerUtil.isNotValid(userId)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.UPDATE_ERROR, "id为空", response);
        } else {
            resultWrapper = sysUserService.findUserBaseInfoById(userId);
        }
        return resultWrapper;
    }

    /**
     * 根据workId模糊查询，获取其总记录数和总页数
     *
     * @param workId 工号
     * @return Json格式数据
     */
    @RequiresPermissions("user:fQuery")
    @PermissionName(group = "用户管理", displayName = "用户筛选")
    @RequestMapping(value = "/f/query/workId", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper fuzzyQueryByWorkIdTotalPages(String workId,
                                                      Integer pageSize,
                                                      HttpServletResponse response) {
        ResultWrapper resultWrapper;
        //1. 判断传入的参数是否有效
        if (StringUtil.isEmpty(workId) || IntegerUtil.isNotValid(pageSize)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            //2. 获取Service层得到的json对象
            resultWrapper = sysUserService.findTotalByWorkIdFuzzyPages(workId, pageSize);
        }
        return resultWrapper;
    }

    /**
     * 根据workId模糊查询
     *
     * @param params 存放参数map，包含
     *               workId 工号
     *               pageNow 当前页
     *               pageSize 页大小
     * @return Json格式数据
     */
    @RequiresPermissions("user:fQuery")
    @PermissionName(group = "用户管理", displayName = "用户筛选")
    @RequestMapping(value = "/f/query/workId", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper fuzzyQueryByWorkId(@RequestBody Map<String, Object> params,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        String workId = params.get("workId").toString();
        Integer pageNow = (Integer) params.get("pageNow");
        Integer pageSize = (Integer) params.get("pageSize");
        ResultWrapper resultWrapper;
        //1. 空值判断
        if (StringUtil.isEmpty(workId) || IntegerUtil.isNotValid(pageNow) || IntegerUtil.isNotValid(pageSize)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            //1.5 计算开始查询的索引
            Integer startIndex = (pageNow - 1) * pageSize;
            //2. 查询数据
            resultWrapper = sysUserService.findUserByWorkIdFuzzyPages(workId, startIndex, pageSize);
            //3. 日志记录
            sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "根据workId模糊查询用户信息", "/admin/f/query/workId", "查询workId为" + workId + ",第" + pageNow + "页, 页大小为" + pageSize + "的用户记录"));
        }// if 空值判断 end
        return resultWrapper;
    }

    /**
     * 根据realName模糊查询，获取其总记录数和总页数
     *
     * @param realName 姓名
     * @return Json格式数据
     */
    @RequiresPermissions("user:fQuery")
    @PermissionName(group = "用户管理", displayName = "用户筛选")
    @RequestMapping(value = "/f/query/name", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper fuzzyQueryByRealNameTotalPages(String realName,
                                                        Integer pageSize,
                                                        HttpServletResponse response) {
        ResultWrapper resultWrapper;
        //1. 判断传入的参数是否有效
        if (StringUtil.isEmpty(realName) || IntegerUtil.isNotValid(pageSize)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            //2. 获取Service层得到的json对象
            resultWrapper = sysUserService.findTotalByRealNameFuzzy(realName, pageSize);
        }
        return resultWrapper;
    }

    /**
     * 根据realName模糊查询
     *
     * @param params 存放json参数，包含
     *               realName 姓名
     *               pageNow 当前页
     *               pageSize 页大小
     * @return Json格式数据
     */
    @RequiresPermissions("user:fQuery")
    @PermissionName(group = "用户管理", displayName = "用户筛选")
    @RequestMapping(value = "/f/query/name", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper fuzzyQueryByRealName(@RequestBody Map<String, Object> params,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        String realName = params.get("realName").toString();
        Integer pageNow = (Integer) params.get("pageNow");
        Integer pageSize = (Integer) params.get("pageSize");
        ResultWrapper resultWrapper;
        //1. 空值判断
        if (StringUtil.isEmpty(realName) || IntegerUtil.isNotValid(pageNow) || IntegerUtil.isNotValid(pageSize)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            //1.5 计算开始查询的索引
            Integer startIndex = (pageNow - 1) * pageSize;
            //2. 查询数据
            resultWrapper = sysUserService.findUserByRealNameFuzzyPages(realName, startIndex, pageSize);
            //3. 日志记录
            sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "根据realName模糊查询用户信息", "/admin/f/query/name", "查询realName为" + realName + ",第" + pageNow + "页, 页大小为" + pageSize + "的用户记录"));
        }// if 空值判断 end
        return resultWrapper;
    }

    /**
     * 更新用户的基本信息
     *
     * @param sysUser 待更新的用户对象
     * @return json格式数据
     */
    @RequiresPermissions("user:update")
    @PermissionName(group = "用户管理", displayName = "用户更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper updateUserBaseInfo(@RequestBody SysUser sysUser,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        ResultWrapper resultWrapper;
        //判断id是否为空，且id是否存在
        if (null == sysUser.getId() || 0 >= sysUser.getId()) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.UPDATE_ERROR, "id为空或不合法", response);
        } else {
            if (null == sysUserService.findRealNameById(sysUser.getId()).getData()) {
                resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.UPDATE_ERROR, "用户不存在", response);
            } else {
                //设置更新者workId
                sysUser.setUpdatedBy(((SysLog) request.getSession().getAttribute(SysLogUtil.SESSION_SYSLOG)).getWorkId());
                //更新对象
                Map<String, Object> res = sysUserService.updateUserBaseInfo(sysUser);
                if(res != null) {
                    resultWrapper = ResultWrapperUtil.setSuccessOf(res);
                } else{
                    resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.UPDATE_ERROR, "邮箱已存在", response);
                }
                //日志记录
                sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "更新用户基本信息", "/admin/update/info", "更新用户(id):" + sysUser.getId()));
            }
        }
        return resultWrapper;
    }

    /**
     * 根据用户id删除用户
     * 不是真删除，而是将Valid设为0，即无效
     *
     * @param params json解析的参数，包含
     *               userId 用户id
     * @return 返回json格式数据
     */
    @RequiresPermissions("user:delete")
    @PermissionName(group = "用户管理", displayName = "用户删除")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper deleteUserByUserId(@RequestBody Map<String, Object> params,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        Integer userId = (Integer) params.get("userId");
        ResultWrapper resultWrapper;
        //判断是否为空值
        if (null == userId || 0 >= userId) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.SELECT_ERROR, "存在空值", response);
        } else {
            //查询id是否存在
            if (null == sysUserService.findRealNameById(userId).getData()) {
                resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.SELECT_ERROR, "id不存在！", response);
            } else {
                //调用删除方法，更新者workId从session中获取
                resultWrapper = sysUserService.deleteUserByUserId(userId, ((SysLog) request.getSession().getAttribute(SysLogUtil.SESSION_SYSLOG)).getWorkId());
                //日志记录
                sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "删除用户", "/admin/del/id", "删除用户(id):" + userId));
            }
        }
        return resultWrapper;
    }


    /**
     * 添加用户基本信息
     *
     * @param sysUser 用户信息
     * @return 返回刚添加的用户id
     */
    @RequiresPermissions("user:add")
    @PermissionName(group = "用户管理", displayName = "用户添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper saveUserBaseInfo(@RequestBody SysUser sysUser,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        ResultWrapper resultWrapper;
        LOGGER.debug(sysUser.toString());
        //判断roleId和sysUser.getDepartment().getId()是否为空，且是否存在
        if (!SysUserUtil.isEffectiveDepartmentAndRoleId(departmentService, sysUser)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.SELECT_ERROR, "部门id或角色id不存在", response);
        } else {
            //获取session中的用户信息
            LOGGER.debug(request.getSession().getId());
            SysLog sysLog = (SysLog) request.getSession().getAttribute(SysLogUtil.SESSION_SYSLOG);
            //将修改者workId记录到sysUser中
            sysUser.setCreatedBy(sysLog.getWorkId());
            sysUser.setUpdatedBy(sysLog.getWorkId());
            //设置账户创建日期
            sysUser.setAccountDate(new Date());
            //TODO 密码加密
            sysUser.setSalt(sysUser.getPassword());
            //判断是否存在空值
            if (SysUserUtil.isSavedAllInfoNull(sysUser)) {
                resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.SELECT_ERROR, "用户信息存在空值", response);
            } else {
                //添加用户信息，并将返回的json对象赋值给resultWrapper
                resultWrapper = sysUserService.saveUserBaseInfo(sysUser);
                if(resultWrapper == null){
                    resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.SAVE_ERROR, "邮箱已存在", response);
                }
                //记录到用户操作记录
                sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "添加用户", "/admin/info/add", "添加用户:" + sysUser.getWorkId()));
            }
        }
        return resultWrapper;
    }

    /**
     * 根据pageSize查询总页数
     *
     * @param pageSize 页大小
     * @return 返回json格式数据
     */
    @RequiresPermissions("user:query")
    @PermissionName(group = "用户管理", displayName = "用户查询")
    @RequestMapping(value = "/list/pages", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper userTotalPages(Integer pageSize,
                                        HttpServletResponse response) {
        ResultWrapper resultWrapper;
        //1. 判断页大小是否有效
        if (IntegerUtil.isNotValid(pageSize)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else {
            //2. 查询获得封装好的json对象
            resultWrapper = sysUserService.findTotalPages(pageSize);
        }//1. 判断页大小是否有效 End
        return resultWrapper;
    }

    /**
     * 分页查询用户信息
     *
     * @param params 参数map，包含
     *               pageNow  当前页数
     *               pageSize 一页查询的数量
     * @return 返回json格式数据
     */
    @RequiresPermissions("user:query")
    @PermissionName(group = "用户管理", displayName = "用户查询")
    @RequestMapping(value = "/list/pages", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper userListPages(@RequestBody Map<String, Object> params,
                                       HttpServletResponse response) {
        Integer pageNow = (Integer) params.get("pageNow");
        Integer pageSize = (Integer) params.get("pageSize");
        ResultWrapper resultWrapper;
        //判断参数是否合法
        if (null == pageNow || null == pageSize
                || 0 >= pageNow || 0 >= pageSize) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.SELECT_ERROR, "参数不合法！", response);
        } else {
            //算出需要查询位置索引
            Integer startIndex = (pageNow - 1) * pageSize;
            //获取查询结果
            resultWrapper = sysUserService.findUserByPages(startIndex, pageSize);
        }
        return resultWrapper;
    }

    /**
     * 查询所有用户的基本信息
     *
     * @return 返回json数据
     */
    @RequiresPermissions("user:query")
    @PermissionName(group = "用户管理", displayName = "用户查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper findAllUser() {
        ResultWrapper resultWrapper;
        resultWrapper = sysUserService.findUserList();
        return resultWrapper;
    }


    @Autowired
    public void setSysLogService(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
