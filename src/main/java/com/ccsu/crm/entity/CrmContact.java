package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 联系人实体类 - 对应crm_contact表
 * 符合PDF客户管理模块：联系人管理
 */
@Data
@TableName("crm_contact")
public class CrmContact {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;       // 关联客户ID
    private String contactName;    // 联系人姓名
    private String position;       // 职位
    private String phone;          // 手机号
    private String email;          // 邮箱
    private String wechat;         // 微信
    private Integer isPrimary;     // 是否主联系人（0否/1是）
    private Date createdAt;
    private Date updatedAt;
}
