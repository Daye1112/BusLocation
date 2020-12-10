package com.ky.gps.controller.manage;

import com.ky.gps.annotation.PermissionName;
import com.ky.gps.entity.ErrorCode;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SbRoute;
import com.ky.gps.service.SbRouteService;
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
import java.text.ParseException;
import java.util.Map;

/**
 * 路线管理api
 *
 * @author Darren
 */
@RequestMapping("/m/route")
@Controller
public class SbRouteManageHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SbRouteManageHandler.class);

    @Autowired
    private SbRouteService sbRouteService;

    /**
     * 根据name和发车时间模糊查询路线信息
     *
     * @return 返回路线信息集合
     */
    @PermissionName(displayName = "路线筛选", group = "路线管理")
    @RequiresPermissions("route:fQuery")
    @ResponseBody
    @RequestMapping(value = "/f/nameAndTime", method = RequestMethod.POST)
    public ResultWrapper fuzzyQueryByNameAndTime(@RequestBody(required = false) Map<String, Object> params,
                                                 HttpServletResponse response,
                                                 HttpServletRequest request) throws ParseException {
        ResultWrapper resultWrapper = null;
        String sbrRouteName = "";
        String startTime = "";
        String endTime = "";
        //空值校验
        if (params != null) {
            if (params.get("sbrRouteName") != null) {
                sbrRouteName = params.get("sbrRouteName").toString();
            }
            if (params.get("startTime") != null
                    && params.get("endTime") != null) {
                startTime = params.get("startTime").toString();
                endTime = params.get("endTime").toString();
            }
            //判断时间区间是否正规
            if (StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime) && !DateUtil.verifyHour(startTime, endTime)) {
                resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.PARAMETER_NOT_VALID, "时间区间错误", response);
            }
        }
        if (resultWrapper == null) {
            resultWrapper = ResultWrapperUtil.setSuccessOf(sbRouteService.findByNameAndTimeFuzzy(sbrRouteName, startTime, endTime));
        }
        return resultWrapper;
    }

    /**
     * 添加路线信息
     *
     * @param sbRoute 待添加的路线对象
     * @return 返回json数据，data为新增的路线信息
     */
    @PermissionName(displayName = "路线添加", group = "路线管理")
    @RequiresPermissions("route:add")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultWrapper save(@RequestBody(required = false) SbRoute sbRoute,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        ResultWrapper resultWrapper;
        //空值校验
        if (SbRouteUtil.veritySbRouteExcludeId(sbRoute)) {
            resultWrapper = ResultWrapperUtil.setSuccessOf(sbRouteService.save(sbRoute));
        } else {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "基本参数不可为空", response);
        }
        return resultWrapper;
    }

    /**
     * 根据id删除路线信息
     *
     * @param params 参数map，包含id
     * @return 返回json对象，data为空
     */
    @PermissionName(displayName = "路线删除", group = "路线管理")
    @RequiresPermissions("route:delete")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResultWrapper delete(@RequestBody(required = false) Map<String, Object> params,
                                HttpServletResponse response,
                                HttpServletRequest request) {
        ResultWrapper resultWrapper;
        //空值校验
        if (params != null
                && params.containsKey("id")
                && params.get("id") != null
                && IntegerUtil.isValid((Integer) params.get("id"))) {
            //获取id
            Integer id = (Integer) params.get("id");
            sbRouteService.deleteById(id);
            resultWrapper = ResultWrapperUtil.setSuccessOf(null);
        } else {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "id不可为空", response);
        }
        return resultWrapper;
    }

    /**
     * 根据id更新路线信息
     *
     * @param sbRoute 待更新的路线对象
     * @return 返回json信息
     */
    @PermissionName(displayName = "路线更新", group = "路线管理")
    @RequiresPermissions("route:update")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultWrapper update(@RequestBody(required = false) SbRoute sbRoute,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        ResultWrapper resultWrapper;
        //空值校验
        if (SbRouteUtil.veritySbRoute(sbRoute)) {
            resultWrapper = ResultWrapperUtil.setSuccessOf(sbRouteService.updateById(sbRoute));
        } else {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "基本参数不可为空", response);
        }
        return resultWrapper;
    }

    /**
     * 查询所有路线信息
     *
     * @return 返回所有路线信息
     */
    @PermissionName(displayName = "路线查询", group = "路线管理")
    @RequiresPermissions("route:query")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultWrapper list() {
        ResultWrapper resultWrapper;
        resultWrapper = ResultWrapperUtil.setSuccessOf(sbRouteService.findAllBaseInfo());
        return resultWrapper;
    }

}
