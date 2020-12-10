package com.ky.gps.entity;

/**
 * @author Daye
 * 操作返回码
 */
public enum ErrorCode {

    /**
     * 数据格式化错误
     */
    PARAMETER_ERROR("参数类型错误！"),
    /**
     * 参数无效
     */
    PARAMETER_NOT_VALID("参数无效"),
    /**
     * 参数格式错误
     */
    PARAMETER_FORMAT_ERROR("参数格式错误"),
    /**
     * 登录账号/密码错误
     */
    Login_ERROR("账号/密码错误"),
    /**
     * 登录失败
     */
    LOGIN_FAILURE("登录失败"),
    /**
     * 未登录
     */
    LOGIN_WARNING("未登录"),
    /**
     * 验证码错误
     */
    VERIFY_ERROR("验证码错误"),
    /**
     * 验证码为空
     */
    VERIFY_EMPTY_ERROR("请先请求生成验证码/验证码过时"),
    /**
     * 查询失败
     */
    SELECT_ERROR("查询失败"),
    /**
     * 添加失败
     */
    SAVE_ERROR("添加失败"),
    /**
     * 更新失败
     */
    UPDATE_ERROR("更新失败"),
    /**
     * 异常错误
     */
    SYSTEM_ERROR("操作异常"),
    /**
     * 空值判断
     */
    EMPTY_ERROR("存在空值"),
    /**
     * 无权限
     */
    INSUFFICIENT_PERMISSION("无操作权限");

    private String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }
}
