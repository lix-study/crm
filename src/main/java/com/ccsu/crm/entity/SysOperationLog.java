package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志实体类 - 对应sys_operation_log表
 * 符合PDF系统管理模块：操作日志
 */
@Data
@TableName("sys_operation_log")
public class SysOperationLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;           // 操作人ID
    private String username;       // 操作人用户名
    private String operation;      // 操作描述
    private String method;         // 请求方法
    private String requestUrl;     // 请求URL
    private String requestMethod;  // HTTP方法
    private String ip;             // 请求IP
    private Long costTime;         // 耗时(ms)
    private Date createTime;
}
