package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报价单实体类 - 对应crm_quote表
 * 符合PDF销售管理模块：报价管理
 */
@Data
@TableName("crm_quote")
public class CrmQuote {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String quoteNo;          // 报价编号
    private Long customerId;         // 关联客户ID
    private Long opportunityId;      // 关联商机ID
    private BigDecimal totalAmount;  // 总金额
    private BigDecimal discount;     // 折扣
    private Date validUntil;         // 有效期至
    private Integer status;          // 状态（0草稿/1待审批/2已通过/3已拒绝）
    private Long ownerId;            // 负责人ID
    private Date createdAt;
    private Date updatedAt;
}
