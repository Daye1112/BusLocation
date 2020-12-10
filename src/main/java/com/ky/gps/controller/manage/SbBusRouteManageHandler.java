package com.ky.gps.controller.manage;

import com.ky.gps.annotation.PermissionName;
import com.ky.gps.entity.*;
import com.ky.gps.service.SbBusRouteService;
import com.ky.gps.util.ResultWrapperUtil;
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
import java.util.Map;

/**
 * 校车路线绑定管理api
 *
 * @author Darren
 */
@Controller
@RequestMapping("/m/busRoute")
public class SbBusRouteManageHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(SbBusRouteManageHandler.class);

    @Autowired
    private SbBusRouteService sbBusRouteService;

    /**
     * 更新绑定信息
     */
    @PermissionName(displayName = "校车路线更新", group = "校车路线绑定")
    @RequiresPermissions("busRoute:update")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultWrapper update(@RequestBody(required = false) Map<String, Object> params,
                                HttpServletResponse response,
                                HttpServletRequest request) {
        ResultWrapper resultWrapper;
        //参数空值校验
        if (!params.isEmpty()
                && params.get("id") != null
                && params.get("sbbrWeek") != null
                && params.get("sbbrStartTime") != null
                && params.get("sbbrEndTime") != null) {
            SbBusRoute sbBusRoute = new SbBusRoute();
            sbBusRoute.setId((Integer) params.get("id"));
            sbBusRoute.setSbbrWeek(params.get("sbbrWeek").toString());
            sbBusRoute.setSbbrStartTime(params.get("sbbrStartTime").toString());
            sbBusRoute.setSbbrEndTime(params.get("sbbrEndTime").toString());
            resultWrapper = ResultWrapperUtil.setSuccessOf(sbBusRouteService.update(sbBusRoute));
        } else {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        }
        return resultWrapper;
    }

    /**
     * 添加校车路线绑定信息
     */
    @PermissionName(displayName = "校车路线添加", group = "校车路线绑定")
    @RequiresPermissions("busRoute:save")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultWrapper save(@RequestBody(required = false) Map<String, Object> params,
                              HttpServletResponse response,
                              HttpServletRequest request) {
        ResultWrapper resultWrapper;
        //参数空值校验
        if (!params.isEmpty()
                && params.get("busId") != null
                && params.get("routeId") != null
                && params.get("sbbrWeek") != null
                && params.get("sbbrStartTime") != null
                && params.get("sbbrEndTime") != null) {
            SbBusRoute sbBusRoute = new SbBusRoute();
            SbBus sbBus = new SbBus();
            sbBus.setId((Integer) params.get("busId"));
            sbBusRoute.setSbBus(sbBus);
            SbRoute sbRoute = new SbRoute();
            sbRoute.setId((Integer) params.get("routeId"));
            sbBusRoute.setSbRoute(sbRoute);
            sbBusRoute.setSbbrWeek(params.get("sbbrWeek").toString());
            sbBusRoute.setSbbrStartTime(params.get("sbbrStartTime").toString());
            sbBusRoute.setSbbrEndTime(params.get("sbbrEndTime").toString());
            resultWrapper = ResultWrapperUtil.setSuccessOf(sbBusRouteService.save(sbBusRoute));
        } else {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        }
        return resultWrapper;
    }

    /**
     * 根据id删除校车路线绑定信息
     *
     * @param params 绑定id
     */
    @PermissionName(displayName = "校车路线删除", group = "校车路线绑定")
    @RequiresPermissions("busRoute:delete")
    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResultWrapper delete(@RequestBody(required = false) Map<String, Object> params,
                                HttpServletResponse response,
                                HttpServletRequest request) {
        ResultWrapper resultWrapper;
        if (!params.isEmpty() && params.get("id") != null) {
            Integer id = (Integer) params.get("id");
            sbBusRouteService.updateValidById(id);
            resultWrapper = ResultWrapperUtil.setSuccessOf(null);
        } else {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "路线id不可为空", response);
        }
        return resultWrapper;
    }

    /**
     * 根据路线id查询路线校车的绑定信息
     *
     * @param params 路线id, 星期week, 开始时间startTime, 结束时间endTime
     */
    @PermissionName(displayName = "校车路线查询", group = "校车路线绑定")
    @RequiresPermissions("busRoute:query")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultWrapper findByRouteId(@RequestBody(required = false) Map<String, Object> params,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        ResultWrapper resultWrapper;
        //空值校验
        if (!params.isEmpty() && params.get("routeId") != null) {
            Integer routeId = (Integer) params.get("routeId");
            String week = "";
            String startTime = "";
            String endTime = "";
            if (params.get("sbbrWeek") != null) {
                week = params.get("sbbrWeek").toString();
            }
            if (params.get("sbbrStartTime") != null) {
                startTime = params.get("sbbrStartTime").toString();
            }
            if (params.get("sbbrEndTime") != null) {
                endTime = params.get("sbbrEndTime").toString();
            }
            resultWrapper = ResultWrapperUtil.setSuccessOf(sbBusRouteService.findByRouteId(routeId, week, startTime, endTime));
        } else {
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, "路线id不可为空", response);
        }
        return resultWrapper;
    }

}
