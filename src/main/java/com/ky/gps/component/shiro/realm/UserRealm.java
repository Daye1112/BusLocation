package com.ky.gps.component.shiro.realm;

import com.ky.gps.enums.SysParamEnum;
import com.ky.gps.service.SbRoleAuthorityService;
import com.ky.gps.service.SbUserRoleService;
import com.ky.gps.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 用户授权和认证Realm
 *
 * @author Daye
 */
public class UserRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);
    /**
     * 用户身份数组
     */
    private final static String[] LOGIN_TYPE = {"manager", "simple"};
    private SysUserService sysUserService;
    private SbUserRoleService sbUserRoleService;
    private SbRoleAuthorityService sbRoleAuthorityService;

    @Override
    public String getName() {
        return "UserRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) principals.getPrimaryPrincipal();
        //查询用户的角色
        List<String> roles = sbUserRoleService.findSysRoleSrSourceBySysUserId((Integer) user.get("id"));
        //查询角色对应的权限
        List<String> permissions = sbRoleAuthorityService.findSaNameBySrSource(roles);
        //创建权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //设置用户权限和角色
        info.addStringPermissions(permissions);
        info.addRoles(roles);
        LOGGER.debug("用户" + user.get("workId") + "权限加载完毕");
        //返回权限信息
        return info;
    }

    /**
     * 认证
     *
     * @param token 令牌
     * @return 返回用户信息
     * @throws AuthenticationException 判断用户登录认证失败原因
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1. 获取登录类型
        String loginType = SecurityUtils.getSubject().getSession().getAttribute(SysParamEnum.SESSION_LOGIN_TYPE.toString()).toString();
        SecurityUtils.getSubject().getSession().removeAttribute(SysParamEnum.SESSION_LOGIN_TYPE.toString());
        //2. 从token中获取登录的用户名
        String workId = token.getPrincipal().toString();
        //3. 查询数据库
        Map<String, Object> user = null;
        if (LOGIN_TYPE[0].equals(loginType)) {
            //管理员
            user = sysUserService.findAdminBaseInfoByWordId(workId);
        } else if (LOGIN_TYPE[1].equals(loginType)) {
            //普通用户
            user = sysUserService.findBaseInfoByWorkId(workId);
        }
        if (user == null) {
            return null;
        }
        //根据登录类型不同，处理用户信息map中的数据
        // 当前用户信息存到session中
        SecurityUtils.getSubject().getSession().setAttribute(SysParamEnum.SESSION_USER_NAME.toString(), user);
        //返回认证信息
        return new SimpleAuthenticationInfo(user, user.get("password"), getName());
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setSbUserRoleService(SbUserRoleService sbUserRoleService) {
        this.sbUserRoleService = sbUserRoleService;
    }

    @Autowired
    public void setSbRoleAuthorityService(SbRoleAuthorityService sbRoleAuthorityService) {
        this.sbRoleAuthorityService = sbRoleAuthorityService;
    }
}
