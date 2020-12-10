package com.ky.gps.controller.manage;

import com.ky.gps.annotation.PermissionName;
import com.ky.gps.entity.ErrorCode;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysAuthority;
import com.ky.gps.service.SysAuthorityService;
import com.ky.gps.util.ResultWrapperUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 权限处理器
 */
@Controller
@RequestMapping(value = "/m/authority")
public class SysAuthorityManageHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(SysAuthorityManageHandler.class);

    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private SysAuthorityService sysAuthorityService;

    /**
     * 查询所有权限
     *
     * @return 返回json格式数据
     */
    @PermissionName(displayName = "权限查询", group = "权限管理")
    @RequiresPermissions("authority:query")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper findAll() {
        return sysAuthorityService.findAll();
    }

    /**
     * 加载程序中的所有权限到数据库中
     *
     * @return 返回json数据
     */
    @PermissionName(displayName = "权限重载", group = "权限管理")
    @RequiresPermissions("authority:reload")
    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper reloadPermission() {
        ResultWrapper resultWrapper;
        LOGGER.debug("重新加载系统权限ing...");
        //0.查询出所有的权限表达式，然后对比，如果已经存在，跳过，不存在添加
        List<String> allSaName = sysAuthorityService.findAllSaName();
        //1. 获取Controller中所有待遇@requestMapper标签的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        Collection<HandlerMethod> methods = handlerMethodMap.values();
        for (HandlerMethod method : methods) {
            //2. 遍历所有方法，判断当前方法是否贴有@RequiresPermissions标签
            RequiresPermissions annotation = method.getMethodAnnotation(RequiresPermissions.class);
            if (annotation != null) {
                //3. 如果有，解析得到权限表达式，封装成Permission对象保存到Permission表中
                //权限表达式
                String value = annotation.value()[0];
                if (allSaName.contains(value)) {
                    continue;
                }
                SysAuthority sysAuthority = new SysAuthority();
                sysAuthority.setSaName(value);
                //设置权限名称和权限组
                sysAuthority.setSaDisplayName(method.getMethodAnnotation(PermissionName.class).displayName());
                sysAuthority.setSaGroup(method.getMethodAnnotation(PermissionName.class).group());
                //保存到数据库
                sysAuthorityService.insertSelective(sysAuthority);
                allSaName = sysAuthorityService.findAllSaName();
            }
        }
        resultWrapper = ResultWrapperUtil.setSuccessOf(null);
        LOGGER.debug("重新加载系统权限...完毕");
        return resultWrapper;
    }

    /**
     * 无权限警告
     *
     * @return json数据
     */
    @RequestMapping(value = "/no", method = RequestMethod.GET)
    @ResponseBody
    public ResultWrapper hasNoPermission(HttpServletResponse response) {
        return ResultWrapperUtil.setErrorAndStatusOf(ErrorCode.INSUFFICIENT_PERMISSION, response);
    }

    @Autowired
    public void setSysAuthorityService(SysAuthorityService sysAuthorityService) {
        this.sysAuthorityService = sysAuthorityService;
    }

    @Autowired
    public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

}
