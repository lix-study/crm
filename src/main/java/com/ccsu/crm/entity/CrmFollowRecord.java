package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 跟进记录实体类 - 对应crm_follow_record表
 * 符合PDF客户管理模块：跟进记录
 */
@Data
@TableName("crm_follow_record")
public class CrmFollowRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;         // 关联客户ID
    private Long opportunityId;      // 关联商机ID（可选）
    private String followWay;        // 跟进方式（电话/微信/邮件/拜访）
    private String content;          // 跟进内容
    private String nextPlan;         // 下次计划
    private Date nextFollowTime;     // 下次跟进时间
    private Long ownerId;            // 跟进人ID
    private Date createdAt;
}
