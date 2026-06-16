package com.ccsu.crm.common;

/**
 * 自定义业务异常 - 符合PDF全局异常处理规范8.4
 * 业务异常返回4xx状态码和错误描述
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
