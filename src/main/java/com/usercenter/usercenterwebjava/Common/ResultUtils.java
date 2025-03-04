package com.usercenter.usercenterwebjava.Common;


/**
 * 返回工具类
 */
public class ResultUtils {
    /**
     * 成功
     *

     */

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>(0, data, "ok");
        response.setCode(0);
        response.setMessage("成功");
        response.setData(data);
        return response;
    }

    /**
     * 失败
     *

     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }
    /**
     * 失败
     *

     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 失败
     *

     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     *
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(), description);
    }
}
