package com.ccsu.crm.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;   // 状态码
    private String msg;   // 提示信息
    private Object data;   // 数据
    private long timestamp;   // 时间戳
    /**
     * 成功响应
     * @param data 数据
     * @return 成功响应结果
     */
    public static Result success(Object data) {
        Result result = new Result(200, null, data, System.currentTimeMillis());
        return result;
    }

    public static Result success(String msg, Object data) {
        Result result = new Result(200, msg, data, System.currentTimeMillis());
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result(200, msg, null, System.currentTimeMillis());
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result(500, msg, null, System.currentTimeMillis());
        return result;
    }

}
