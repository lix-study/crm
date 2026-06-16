package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户实体类 - 对应sys_user表
 */
@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String email;
    private String avatar;
    private Integer status;       // 状态（0禁用/1启用）
    private Long roleId;          // 关联角色ID
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Integer deleted;      // 逻辑删除（0未删除/1已删除）
}
