package com.ccsu.crm.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果类 - 符合PDF接口规范8.2
 * { "code": 200, "message": "success", "data": {...}, "timestamp": 1717000000000 }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;        // 业务状态码（200成功，4xx客户端错误，5xx服务端错误）
    private String message;  // 响应消息描述
    private Object data;     // 业务数据
    private long timestamp;  // 响应时间戳

    /**
     * 成功响应（带数据）
     */
    public static Result success(Object data) {
        return new Result(200, "success", data, System.currentTimeMillis());
    }

    /**
     * 成功响应（带消息和数据）
     */
    public static Result success(String message, Object data) {
        return new Result(200, message, data, System.currentTimeMillis());
    }

    /**
     * 成功响应（仅消息）
     */
    public static Result success(String message) {
        return new Result(200, message, null, System.currentTimeMillis());
    }

    /**
     * 错误响应（默认500）
     */
    public static Result error(String message) {
        return new Result(500, message, null, System.currentTimeMillis());
    }

    /**
     * 错误响应（自定义状态码）
     */
    public static Result error(int code, String message) {
        return new Result(code, message, null, System.currentTimeMillis());
    }
}
