package com.ccsu.crm.common;

/**
 * 认证/授权异常 - 符合PDF全局异常处理规范8.4
 * 认证异常返回401，授权异常返回403
 */
public class UnauthorizedException extends RuntimeException {

    private int code;

    public UnauthorizedException(String message) {
        super(message);
        this.code = 401;
    }

    public UnauthorizedException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
