package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 营销活动实体类 - 对应crm_marketing表
 * 符合PDF营销管理模块
 */
@Data
@TableName("crm_marketing")
public class CrmMarketing {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String campaignName;    // 活动名称
    private String type;            // 活动类型（邮件/短信/线下活动/线上推广）
    private BigDecimal budget;      // 预算
    private Date startDate;         // 开始日期
    private Date endDate;           // 结束日期
    private Integer status;         // 状态（0草稿/1进行中/2已结束/3已取消）
    private String targetAudience;  // 目标受众
    private Integer reachCount;     // 触达人数
    private Integer convertCount;   // 转化人数
    private Long ownerId;           // 负责人ID
    private Date createdAt;
    private Date updatedAt;
}
