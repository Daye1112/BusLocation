package com.ky.gps.enums;

/**
 * @author Daye
 * 系统参数枚举
 */
public enum SysParamEnum {
    /**
     * 存入Session中的日志name
     */
    SESSION_SYS_LOG_NAME("sysLog"),
    /**
     * 存入Session中的user
     */
    SESSION_USER_NAME("user"),
    /**
     * 存入session中的用户登录类型
     */
    SESSION_LOGIN_TYPE("loginType"),
    /**
     * 学生source
     */
    LOGIN_TYPE_STUDENT("student"),
    /**
     * 验证码
     */
    VERIFY_CODE_VAL("verifyCodeValue");

    private String paramName;

    SysParamEnum(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public String toString() {
        return paramName;
    }
}
