package com.ccsu.crm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商机实体类 - 对应crm_opportunity表（PDF 7.3.2）
 */
@Data
@TableName("crm_opportunity")
public class CrmOpportunity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long customerId;         // 关联客户ID
    private String opportunityName;   // 商机名称
    private BigDecimal amount;        // 商机金额
    private Integer stage;            // 阶段（1初步/2需求/3报价/4谈判/5赢单/6输单）
    private Integer probability;      // 成交概率（%）
    private Date expectedDate;        // 预期成交日期
    private Long ownerId;             // 负责人ID
    private Date createdAt;
}
