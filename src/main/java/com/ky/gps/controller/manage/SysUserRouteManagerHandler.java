package com.ky.gps.controller.manage;

import com.ky.gps.annotation.PermissionName;
import com.ky.gps.entity.ErrorCode;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.service.SbUserBusService;
import com.ky.gps.service.SysLogService;
import com.ky.gps.util.ResultWrapperUtil;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户路线处理器
 *
 * @author darren
 * @date 19/11/18 00:03
 */
@RestController
@RequestMapping("/m/userRoute")
public class SysUserRouteManagerHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(SysUserRouteManagerHandler.class);

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SbUserBusService sbUserBusService;

    @PermissionName(displayName = "用户路线更新", group = "用户路线管理")
    @RequiresPermissions("userRoute:update")
    @PostMapping("/update")
    public ResultWrapper update(@RequestBody Update update, HttpServletResponse response){
        ResultWrapper resultWrapper;
        if(update == null || update.getRouteIds() == null || update.getUserId() == null){
            resultWrapper = ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.EMPTY_ERROR, response);
        } else{
            sbUserBusService.update(update.getUserId(), update.getRouteIds());
            resultWrapper = ResultWrapperUtil.setSuccessOf(null);
        }
        return resultWrapper;
    }

    @PermissionName(displayName = "用户路线查询", group = "用户路线管理")
    @RequiresPermissions("userRoute:query")
    @GetMapping("/list")
    public ResultWrapper list( QueryCriteria queryCriteria){
        System.out.println(queryCriteria);
        Map<String, Object> params = new HashMap<>(16);
        if(queryCriteria != null) {
            params.put("department", queryCriteria.getDepartment());
            params.put("workId", queryCriteria.getWorkId());
            params.put("realName", queryCriteria.getRealName());
        }
        List<Map<String, Object>> list = sbUserBusService.list(params);
        return ResultWrapperUtil.setSuccessOf(list);
    }
}

@Data
class Update {
    private Integer userId;
    private List<Integer> routeIds;
}

@Data
class QueryCriteria{
    private String department;
    private String workId;
    private String realName;

    public void setDepartment(String department) {
        if(StringUtils.isEmpty(department)){
            this.department = department;
        } else{
            this.department = "%" + department + "%";
        }
    }

    public void setWorkId(String workId) {
        if(StringUtils.isEmpty(workId)){
            this.workId = workId;
        } else{
            this.workId = "%" + workId + "%";
        }
    }

    public void setRealName(String realName) {
        if(StringUtils.isEmpty(realName)){
            this.realName = realName;
        } else{
            this.realName = "%" + realName + "%";
        }
    }
}