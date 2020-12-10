package com.ky.gps.entity;

import java.io.Serializable;

/**
 * @author Daye
 * 存放操作信息和返回码等信息
 */
public class ResultWrapper implements Serializable {

    /**
     * 操作返回码：0-失败；1-成功
     */
    private Integer code;
    /**
     * 是否操作成功
     */
    private Boolean success;
    /**
     * 返回的提示信息
     */
    private String message;
    /**
     * 如果含返回信息，存放进data中
     */
    private Object data;

    /**
     * 有参构造函数
     */
    public ResultWrapper(Integer code, Boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 无参构造函数
     */
    public ResultWrapper() {
    }

    /**
     * getter/setter
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
