package com.ky.gps.controller.general;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Daye
 * 跳转到api文档接口
 */
@RequestMapping("/api")
@Controller
public class SkipApiWatcherHandler {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toApiIndex() {
        return "api/index_api";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toAdminApi() {
        return "api/general/login_api";
    }

    @RequestMapping(value = "/simulation", method = RequestMethod.GET)
    public String toSimulationApi() {
        return "api/general/simulation_posision_api";
    }

    @RequestMapping(value = "/deptManage", method = RequestMethod.GET)
    public String toDeptManageApi() {
        return "api/backstage/dept_manage_api";
    }

    @RequestMapping(value = "/manageSelf", method = RequestMethod.GET)
    public String toManagerSelfApi() {
        return "api/backstage/manager_self_api";
    }

    @RequestMapping(value = "/userManage", method = RequestMethod.GET)
    public String toUserManageApi() {
        return "api/backstage/user_manage_api";
    }

    @RequestMapping(value = "/roleManage", method = RequestMethod.GET)
    public String toRoleManage() {
        return "api/backstage/role_manage_api";
    }

    @RequestMapping(value = "/authorityManage", method = RequestMethod.GET)
    public String toAuthorityManage() {
        return "api/backstage/authority_manage_api";
    }

    @RequestMapping(value = "/roleAuthority", method = RequestMethod.GET)
    public String toRoleAuthorityManage() {
        return "api/backstage/roleauthority_manage_api";
    }

    @RequestMapping(value = "/userRoleManage", method = RequestMethod.GET)
    public String toUserRoleManage() {
        return "api/backstage/userrole_manage_api";
    }

    @RequestMapping(value = "/stationManage", method = RequestMethod.GET)
    public String toStationManage() {
        return "api/backstage/station_manage_api";
    }

    @RequestMapping(value = "/routeManage", method = RequestMethod.GET)
    public String toRouteManage() {
        return "api/backstage/route_manage_api";
    }

    @RequestMapping(value = "/busManage", method = RequestMethod.GET)
    public String toBusManage() {
        return "api/backstage/bus_manage_api";
    }

    @RequestMapping(value = "/routeStationManage", method = RequestMethod.GET)
    public String toRouteStationManage() {
        return "api/backstage/routestation_manage_api";
    }

    @RequestMapping(value = "/busRouteManage", method = RequestMethod.GET)
    public String toBusRouteManage() {
        return "api/backstage/busroute_manage_api";
    }

    @RequestMapping(value = "/logManage", method = RequestMethod.GET)
    public String toLogManage() {
        return "api/backstage/log_manage_api";
    }

    @RequestMapping(value = "/busPositionHis", method = RequestMethod.GET)
    public String toBusPositionHisManage() {
        return "api/backstage/buspositionHis_manage_api";
    }

    @RequestMapping(value = "/routeStationQuery", method = RequestMethod.GET)
    public String toRouteStationQuery() {
        return "api/client/routestation_query_api";
    }

    @RequestMapping(value = "/routeQuery", method = RequestMethod.GET)
    public String toRouteQuery() {
        return "api/client/route_query_api";
    }

    @RequestMapping(value = "/busPositionQuery", method = RequestMethod.GET)
    public String toBusPositionQuery() {
        return "api/client/busposition_query_api";
    }

    @RequestMapping(value = "/emailApi", method = RequestMethod.GET)
    public String toEmailApi() {
        return "api/client/email_api";
    }

    @RequestMapping(value = "/userSelf", method = RequestMethod.GET)
    public String toUserSelf() {
        return "api/client/user_self_api";
    }

    @RequestMapping(value = "/userRoute", method = RequestMethod.GET)
    public String toUserRoute() {
        return "api/backstage/userroute_manage_api";
    }
}
