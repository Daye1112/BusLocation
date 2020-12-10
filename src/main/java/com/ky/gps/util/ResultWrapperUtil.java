package com.ky.gps.util;

import com.ky.gps.entity.ErrorCode;
import com.ky.gps.entity.ResultWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Daye
 * 对resultWrapper进行封装并返回封装好后的对象
 */
public class ResultWrapperUtil {

    /**
     * 操作成功，将object存入待返回的对象中
     *
     * @param object 需要返回前端的data
     * @return 返回封装信息类
     */
    public static ResultWrapper setSuccessOf(Object object) {
        //将操作成功信息封装到结果对象中返回
        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setCode(1);
        resultWrapper.setSuccess(true);
        resultWrapper.setMessage("操作成功！");
        resultWrapper.setData(object);
        return resultWrapper;
    }

    /**
     * 操作失败，将错误提示存入json对象中
     *
     * @param errorCode 错误信息
     * @return 返回封装信息类
     */
    public static ResultWrapper setErrorOf(ErrorCode errorCode) {
        //将操作失败信息封装到结果对象中返回
        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setCode(0);
        resultWrapper.setSuccess(false);
        resultWrapper.setMessage(errorCode.toString());
        resultWrapper.setData(null);
        return resultWrapper;
    }

    /**
     * 操作失败，并失败提示加入错误信息中
     *
     * @param errorCode    错误信息
     * @param errorMessage 额外的错误提示
     * @return 返回封装信息类
     */
    public static ResultWrapper setErrorOf(ErrorCode errorCode, String errorMessage) {
        //将操作失败信息和额外提示信息封装到结果对象中返回
        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setCode(0);
        resultWrapper.setSuccess(false);
        resultWrapper.setMessage(errorCode.toString() + " " + errorMessage);
        resultWrapper.setData(null);
        return resultWrapper;
    }

    /**
     * 操作失败，将错误提示存入json对象中
     *
     * @param errorCode 错误信息
     * @return 返回封装信息类
     */
    public static ResultWrapper setErrorAndStatusOf(ErrorCode errorCode,
                                                    HttpServletResponse response) {
        response.setStatus(400);
        //将操作失败信息封装到结果对象中返回
        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setCode(0);
        resultWrapper.setSuccess(false);
        resultWrapper.setMessage(errorCode.toString());
        resultWrapper.setData(null);
        return resultWrapper;
    }

    /**
     * 操作失败，并失败提示加入错误信息中
     *
     * @param errorCode    错误信息
     * @param errorMessage 额外的错误提示
     * @return 返回封装信息类
     */
    public static ResultWrapper setErrorAndStatusOf(ErrorCode errorCode,
                                                    String errorMessage,
                                                    HttpServletResponse response) {
        response.setStatus(400);
        //将操作失败信息和额外提示信息封装到结果对象中返回
        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setCode(0);
        resultWrapper.setSuccess(false);
        resultWrapper.setMessage(errorCode.toString() + " " + errorMessage);
        resultWrapper.setData(null);
        return resultWrapper;
    }
}
