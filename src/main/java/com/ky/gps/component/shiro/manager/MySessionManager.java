package com.ky.gps.component.shiro.manager;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 重写shiro默认会话管理器
 *
 * @author Daye
 */
public class MySessionManager extends DefaultWebSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(MySessionManager.class);

    private static final String AUTHORIZATION = "Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
        super();
        setGlobalSessionTimeout(DEFAULT_GLOBAL_SESSION_TIMEOUT / 2);
//        setSessionDAO(new EnterpriseCacheSessionDAO());
//        setSessionIdCookie(new SimpleCookie("SHIROSESSIONID"));
//        setSessionIdCookieEnabled(true);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //如果请求头中有 Authorization 则其值为sessionId
        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getRequestURL().indexOf("/login") != -1) {
            return super.getSessionId(request, response);
        } else if (!StringUtils.isEmpty(sessionId)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }
}
