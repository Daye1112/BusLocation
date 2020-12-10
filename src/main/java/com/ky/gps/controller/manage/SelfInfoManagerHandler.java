package com.ky.gps.controller.manage;

import com.ky.gps.entity.ErrorCode;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysLog;
import com.ky.gps.entity.SysUser;
import com.ky.gps.service.SysLogService;
import com.ky.gps.service.SysUserService;
import com.ky.gps.util.ResultWrapperUtil;
import com.ky.gps.util.SysLogUtil;
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
 * 管理员自身信息管理处理器
 *
 * @author Daye
 */
@Controller
@RequestMapping(value = "/m/self")
public class SelfInfoManagerHandler {

    /**
     * 日志打印对象
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(SelfInfoManagerHandler.class);

    private SysUserService sysUserService;
    private SysLogService sysLogService;

    /**
     * 修改用户自身密码
     *
     * @param models  用于存放oldPassword、newPassword的键值对
     * @param request request域
     * @return json格式数据，data为null
     */
    @RequestMapping(value = "/modify/pwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper modifyPassword(@RequestBody Map<String, Object> models,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        ResultWrapper resultWrapper;
        String oldPassword = models.get("oldPassword").toString();
        String newPassword = models.get("newPassword").toString();
        //判断是否含有空值
        if (null == oldPassword || "".equals(oldPassword)
                || null == newPassword || "".equals(newPassword)) {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.SELECT_ERROR, "存在空值", response);
        } else {
            //获取Session中的log对象
            SysLog sysLog = (SysLog) request.getSession().getAttribute(SysLogUtil.SESSION_SYSLOG);
            //判断原密码是否正确
            if (null == sysUserService.findRealNameByPasswordAndUserId(oldPassword, sysLog.getUserId()).getData()) {
                resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.UPDATE_ERROR, "原密码错误", response);
            } else {
                //创建用户对象，将修改后的用户信息存入
                SysUser sysUser = new SysUser();
                //设置id
                sysUser.setId(sysLog.getUserId());
                //设置新密码
                sysUser.setPassword(newPassword);
                //TODO 密码加密
                sysUser.setSalt(newPassword);
                //修改密码
                resultWrapper = sysUserService.updatePassword(sysUser);
                //设置密码修改日期
                sysUser.setLastPsdDate(new Date());
                //设置更新者workId
                sysUser.setUpdatedBy(sysLog.getWorkId());
                //记录日志
                sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "管理员更改自身密码", "/m/self/modify/pwd", "修改用户密码(id):" + sysLog.getUserId()));
            }
        }
        return resultWrapper;
    }

    /**
     * 用户更新基本信息
     * realName、idCard、phone、email
     *
     * @param sysUser 更新后的用户对象
     * @param request request域
     * @return json个数数据，data为null
     */
    @RequestMapping(value = "/update/info", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper updateSelfInfo(@RequestBody SysUser sysUser, HttpServletRequest request) {
        ResultWrapper resultWrapper;
        //获取session中的log对象
        SysLog sysLog = (SysLog) request.getSession().getAttribute(SysLogUtil.SESSION_SYSLOG);
        //设置待更新的用户id
        sysUser.setId(sysLog.getUserId());
        //设置更新者workId
        sysUser.setUpdatedBy(sysLog.getWorkId());
        //更新对象
        resultWrapper = ResultWrapperUtil.setSuccessOf(sysUserService.updateUserBaseInfo(sysUser));
        //日志记录
        sysLogService.saveSysLog(SysLogUtil.setOperateInfo(request, "管理员更新自身基本信息", "/m/self/update/info", "更新用户(id):" + sysUser.getId()));
        return resultWrapper;
    }

    @Autowired
    public void setSysLogService(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
