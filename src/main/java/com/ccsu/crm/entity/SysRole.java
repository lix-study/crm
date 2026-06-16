package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 角色实体类 - 对应sys_role表
 * 符合PDF系统管理模块：基于RBAC模型的角色定义与权限分配
 */
@Data
@TableName("sys_role")
public class SysRole {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String roleName;       // 角色名称
    private String roleCode;       // 角色编码
    private String description;    // 描述
    private Integer status;        // 状态（0禁用/1启用）
    private Date createTime;
    private Date updateTime;
}
